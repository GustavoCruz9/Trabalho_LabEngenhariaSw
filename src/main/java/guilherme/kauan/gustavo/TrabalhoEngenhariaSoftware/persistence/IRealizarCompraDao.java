package guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Ingresso;

public interface IRealizarCompraDao {
	public Evento buscarEvento(Evento evento) throws SQLException, ClassNotFoundException;
	public String finalizarCompra(List<Ingresso> ingressos, Acesso acesso) throws SQLException, ClassNotFoundException;
}
