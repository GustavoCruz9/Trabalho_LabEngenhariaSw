package guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.persistence;

import java.sql.SQLException;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Cliente;

public interface IAtualizarCadastrarClienteDao {
	public String iuCliente(String acao, Cliente c) throws SQLException, ClassNotFoundException;
}
