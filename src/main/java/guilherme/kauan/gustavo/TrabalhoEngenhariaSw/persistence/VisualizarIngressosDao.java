package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Ingresso;

@Repository
public class VisualizarIngressosDao implements IVisualizarIngressosDao {

	@Autowired
	GenericDao gDao;

	public List<Evento> buscaEvento(Acesso acesso) throws SQLException, ClassNotFoundException {

		List<Evento> eventos = new ArrayList<>();

		Connection c = gDao.getConnection();
		String sql = """
				Select Distinct(e.codigo), e.titulo
				from Ingresso i, Evento e, Cliente c, Acesso ac, Compra cp
				where i.eventoCodigo = e.codigo
				and i.notaFiscal = cp.notaFiscal
				and cp.clienteCpf = c.cpf
				and c.loginUsuario = ac.usuario
				and ac.usuario = ?
				""";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, acesso.getUsuario());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			Evento evento = new Evento();

			evento.setCodigo(rs.getInt("codigo"));
			evento.setTitulo(rs.getString("titulo"));

			eventos.add(evento);

		}

		ps.close();
		c.close();

		return eventos;
	}

	@Override
	public List<Ingresso> buscaIngressos(Evento evento, Acesso acesso) throws SQLException, ClassNotFoundException {
		
		List<Ingresso> ingressos = new ArrayList<>();

		Connection c = gDao.getConnection();
		String sql = """
				Select Distinct(i.codigo), i.preco, i.setor, i.tipo, e.dataEvento, e.titulo 
				from Ingresso i, Evento e, Cliente c, Acesso ac, Compra cp
				where i.eventoCodigo = e.codigo 
				and i.notaFiscal = cp.notaFiscal 
				and cp.clienteCpf = c.cpf 
				and c.loginUsuario = ac.usuario 
				and ac.usuario = ?
				and e.codigo = ?
				""";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, acesso.getUsuario());
		ps.setInt(2, evento.getCodigo());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			Ingresso ingresso = new Ingresso();
			evento = new Evento();
			
			ingresso.setCodigo(rs.getInt("codigo"));
			ingresso.setPreco(rs.getDouble("preco"));
			ingresso.setSetor(rs.getString("setor"));
			ingresso.setTipo(rs.getString("tipo"));
			evento.setData(rs.getDate("dataEvento").toLocalDate());
			evento.setTitulo(rs.getString("titulo"));
			ingresso.setEvento(evento);
			
			ingressos.add(ingresso);
		}

		ps.close();
		c.close();

		return ingressos;
	}

	@Override
	public String cancelarIngresso(Ingresso ingresso) throws SQLException, ClassNotFoundException {
		
		Connection c = gDao.getConnection();
		String sql = "delete Ingresso where codigo = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, ingresso.getCodigo());
		ps.execute();
		
		ps.close();
		c.close();
		
		return "Ingresso excluido com sucesso";
	}



}
