package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Ingresso;

public interface IRealizarCompraDao {
	public Evento buscarEvento(Evento evento) throws SQLException, ClassNotFoundException;
	public String finalizarCompra(List<Ingresso> ingressos, Acesso acesso) throws SQLException, ClassNotFoundException;
}
