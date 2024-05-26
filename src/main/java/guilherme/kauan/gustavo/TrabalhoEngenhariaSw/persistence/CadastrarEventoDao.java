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

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Artista;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;

@Repository
public class CadastrarEventoDao implements ICadastrarEventoDao {

	@Autowired
	GenericDao gDao;

	@Override
	public String sp_iuEvento(String acao, Evento evento) throws SQLException, ClassNotFoundException {
		
		if(acao.equalsIgnoreCase("U")) {
			excluirArtistas(evento);
		}

		Connection c = gDao.getConnection();
		String sql = "{CALL sp_iuEvento (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		CallableStatement cs = c.prepareCall(sql);

		cs.setString(1, acao);
		cs.setInt(2, evento.getCodigo());
		cs.setString(3, evento.getTitulo());
		cs.setString(4, evento.getHoraInicio().toString());
		cs.setString(5, evento.getHoraFim().toString());
		cs.setString(6, evento.getData().toString());
		cs.setString(7, evento.getGenero());
		cs.setDouble(8, evento.getValor());
		cs.setString(9, evento.getLinkImagem());
		cs.setString(10, evento.getResponsavel().getAcesso().getUsuario());
		cs.setString(11, evento.getStatusEvento());
		cs.registerOutParameter(12, Types.VARCHAR);

		cs.execute();
		String saida = cs.getString(12);

		cs.close();
		c.close();

		cadastraArtista(evento);

		return saida;
	}
	
	private void excluirArtistas(Evento evento) throws ClassNotFoundException, SQLException {
		Connection c = gDao.getConnection();
		String sql = "delete Evento_Artista where eventoCodigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, evento.getCodigo());
		ps.execute();
	}

	private void cadastraArtista(Evento evento) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();

		for (Artista a : evento.getArtistas()) {
			String sql = "Insert into Evento_Artista Values(?, ?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, a.getCodigo());
			ps.setInt(2, evento.getCodigo());
			ps.execute();
		}

		c.close();
	}

	@Override
	public Evento buscaEvento(Evento evento) throws SQLException, ClassNotFoundException {
		
		List<Artista> artistas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "select * from Evento where codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, evento.getCodigo());
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			
			evento = new Evento();
			
			evento.setCodigo(rs.getInt("codigo"));
			evento.setTitulo(rs.getString("titulo"));
			evento.setLinkImagem(rs.getString("linkImagem"));
			evento.setStatusEvento(rs.getString("statusEvento"));
			evento.setValor(rs.getDouble("valor"));
			evento.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
			evento.setHoraFim(rs.getTime("horaFim").toLocalTime());
			evento.setData(rs.getDate("dataEvento").toLocalDate());	
		}
		
		sql = """
				select a.*
				from Artista a, Evento_Artista ea
				where  a.codigo = ea.artistaCodigo 
				and ea.eventoCodigo = ?
				""";
		
		ps = c.prepareStatement(sql);
		ps.setInt(1, evento.getCodigo());
		rs = ps.executeQuery();
		
		while(rs.next()) {
			
			Artista artista = new Artista();
			artista.setCodigo(rs.getInt("codigo"));
			artista.setNome(rs.getString("nome"));
			artista.setGenero(rs.getString("genero"));
			
			artistas.add(artista);
		}
		
		evento.setArtistas(artistas);
		
		return evento;
	}

	public List<Artista> listaArtistasComParam(String pesquisarArtista) throws SQLException, ClassNotFoundException {

		List<Artista> artistas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "select * from Artista where nome like '%' + ? + '%'";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, pesquisarArtista);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			Artista artista = new Artista();

			artista = new Artista();
			artista.setCodigo(rs.getInt("codigo"));
			artista.setNome(rs.getString("nome"));
			artista.setGenero(rs.getString("genero"));

			artistas.add(artista);

		}

		return artistas;
	}


}
