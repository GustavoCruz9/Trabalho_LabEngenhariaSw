package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;

public interface IIndexDao {
	public List<Evento> buscaEventos() throws SQLException, ClassNotFoundException;
	public List<Evento> buscaEventosComParam(Evento e) throws SQLException, ClassNotFoundException;
}
