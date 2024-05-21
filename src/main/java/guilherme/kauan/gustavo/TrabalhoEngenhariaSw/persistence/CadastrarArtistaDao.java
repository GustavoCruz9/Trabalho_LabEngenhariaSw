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

@Repository
public class CadastrarArtistaDao implements ICadastrarArtistaDao<Artista> {
	
	@Autowired
	GenericDao gDao;

	@Override
	public String iuArtista(Artista artista, String acao) throws SQLException, ClassNotFoundException {
		
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_iuArtista (?, ?, ?, ?, ?)}";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setInt(2, artista.getCodigo());
		cs.setString(3, artista.getNome());
		cs.setString(4, artista.getGenero());
		cs.registerOutParameter(5, Types.VARCHAR);
		
		cs.execute();
		String saida = cs.getString(5);

		cs.close();
		c.close();

		return saida;
	}

	@Override
	public Artista buscarArtista(Artista artista) throws SQLException, ClassNotFoundException {
		
		Connection c = gDao.getConnection();
		String sql = "select * from Artista where codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setInt(1, artista.getCodigo());

		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			artista = new Artista();
			artista.setCodigo(rs.getInt("codigo"));
			artista.setNome(rs.getString("nome"));
			artista.setGenero(rs.getString("genero"));
		}
		
		rs.close();
		c.close();
		
		return artista;
	}

	@Override
	public List<Artista> listarArtistas() throws SQLException, ClassNotFoundException {
		
		List<Artista> artistas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "select * from Artista";
		

		PreparedStatement ps = c.prepareStatement(sql);

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
