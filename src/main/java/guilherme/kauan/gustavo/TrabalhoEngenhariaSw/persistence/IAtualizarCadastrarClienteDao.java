package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.SQLException;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Cliente;

public interface IAtualizarCadastrarClienteDao {
	public String iuCliente(String acao, Cliente c) throws SQLException, ClassNotFoundException;
}
