package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;

@Repository
public class IndexDao implements IIndexDao{
	
	@Autowired
	GenericDao gDao;
	

	@Override
	public List<Evento> buscaEventos() throws SQLException, ClassNotFoundException{
		
		List<Evento> eventos = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				Select Top(8) codigo, titulo, dataEvento, linkImagem from Evento 
				where statusEvento like 'Ativo'
				Order By dataEvento desc
				""";
		
		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			
			 Evento e = new Evento();
			 
			 e.setCodigo(rs.getInt("codigo"));
			 e.setTitulo(rs.getString("titulo"));
			 e.setData(rs.getDate("dataEvento").toLocalDate());
			 e.setLinkImagem(rs.getString("linkImagem"));
			 
			 eventos.add(e);			 
		}
		
		return eventos;
	}

	public List<Evento> buscaEventosComParam(Evento e) throws SQLException, ClassNotFoundException {
		
		List<Evento> eventos = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = """
				Select codigo, titulo, dataEvento, linkImagem from Evento where titulo Like '%' + ? +'%'
				""";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ps.setString(1, e.getTitulo());

		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			
			 e = new Evento();
			 
			 e.setCodigo(rs.getInt("codigo"));
			 e.setTitulo(rs.getString("titulo"));
			 e.setData(rs.getDate("dataEvento").toLocalDate());
			 e.setLinkImagem(rs.getString("linkImagem"));
			 
			 
			 eventos.add(e);			 
		}
		
		return eventos;
	}

}
