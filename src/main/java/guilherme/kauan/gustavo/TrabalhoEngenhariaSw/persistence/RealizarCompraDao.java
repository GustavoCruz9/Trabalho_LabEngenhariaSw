package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Artista;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Ingresso;

@Repository
public class RealizarCompraDao implements IRealizarCompraDao {

	@Autowired
	GenericDao gDao;

	public Evento buscarEvento(Evento evento) throws SQLException, ClassNotFoundException {

		List<Artista> artistas = new ArrayList<>();

		Connection c = gDao.getConnection();
		String sql = """
				Select Distinct e.*, a.nome
				from Evento e, Artista a, Evento_Artista ea
				where e.codigo = ea.eventoCodigo 
				and a.codigo = ea.artistaCodigo 
				and e.codigo = ?
				""";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, evento.getCodigo());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			evento = new Evento();
			evento.setCodigo(rs.getInt("codigo"));
			evento.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
			evento.setHoraFim(rs.getTime("horaFim").toLocalTime());
			evento.setData(rs.getDate("dataEvento").toLocalDate());
			evento.setTitulo(rs.getString("titulo"));
			evento.setGenero(rs.getString("genero"));
			evento.setValor(rs.getDouble("valor"));
			evento.setLinkImagem(rs.getString("linkImagem"));

			Artista a = new Artista();
			a.setNome(rs.getString("nome"));
			artistas.add(a);

		}

		while (rs.next()) {

			Artista a = new Artista();
			a.setNome(rs.getString("nome"));
			artistas.add(a);

		}

		evento.setArtistas(artistas);

		return evento;
	}

	public String finalizarCompra(List<Ingresso> ingressos, Acesso acesso) throws SQLException, ClassNotFoundException {

		Connection c = gDao.getConnection();
		
		int contadorPista = 0;
		int contadorFrontStage = 0;
		int contadorCamarote = 0;
        
        for (Ingresso ingresso : ingressos) {
          
            if (ingresso.getTipo().equalsIgnoreCase("Pista")) {
                contadorPista++;
            }
            if (ingresso.getTipo().equalsIgnoreCase("FrontStage")) {
            	contadorFrontStage++;
            }
            if (ingresso.getTipo().equalsIgnoreCase("Camarote")) {
            	contadorCamarote++;
            }         
        }
        
        String sql = "{CALL sp_verificaIngresso(?, ?, ?, ?, ?)}";
        
        CallableStatement cs = c.prepareCall(sql);
		
        cs.setInt(1, contadorPista);
        cs.setInt(2, contadorCamarote);
        cs.setInt(3, contadorFrontStage);
        cs.setInt(4, ingressos.get(0).getCodigo());
        cs.registerOutParameter(5, Types.INTEGER);
        
        cs.execute();
		int status = cs.getInt(5);
		cs.close();
		
		
		if(status == 1) {
			
			int notaFiscal = cadastrarCompra(ingressos, acesso);
			
			for(Ingresso ingresso : ingressos) {
				sql = "insert into Ingresso (preco, tipo, setor, eventoCodigo, notaFiscal) values (?, ?, ?, ?, ?)";
				
				PreparedStatement ps = c.prepareStatement(sql);
				
				ps.setDouble(1, ingresso.getPreco());
				ps.setString(2, ingresso.getTipo());
				ps.setString(3, ingresso.getSetor());
				ps.setInt(4, ingresso.getEvento().getCodigo());
				ps.setInt(5, notaFiscal);
				
				ps.execute();
			}
			
			c.close();
			return "Ingresso comprado com sucesso";
		}else {
			c.close();
			return null;
		}
	}

	private int cadastrarCompra(List<Ingresso> ingressos, Acesso acesso) throws SQLException, ClassNotFoundException {

		double valorTotal = 0.0;
		// calculando valor total da compra com o aprametro da lista de ingressos
		for (Ingresso ingresso : ingressos) {
			valorTotal += ingresso.getPreco();
		}

		Connection c = gDao.getConnection();
		String sql = "{CALL sp_compra (?, ?, ?, ?, ?)}";

		CallableStatement cs = c.prepareCall(sql);
		
		cs.setDouble(1, valorTotal);
		cs.setString(2, acesso.getUsuario());
		cs.setInt(3, ingressos.size());
		cs.setInt(4, ingressos.get(0).getEvento().getCodigo());
		cs.registerOutParameter(5, Types.INTEGER);
		
		cs.execute();
		int notaFiscal = cs.getInt(5);
		
		cs.close();
		c.close();

		return notaFiscal;
	}

}
