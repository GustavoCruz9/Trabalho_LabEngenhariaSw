package guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Ingresso;

public interface IVisualizarIngressosDao {
	public List<Evento> buscaEvento(Acesso acesso) throws SQLException, ClassNotFoundException;
	public List<Ingresso> buscaIngressos(Evento evento, Acesso acesso) throws SQLException, ClassNotFoundException;
	public String cancelarIngresso(Ingresso ingresso) throws SQLException, ClassNotFoundException;
}
