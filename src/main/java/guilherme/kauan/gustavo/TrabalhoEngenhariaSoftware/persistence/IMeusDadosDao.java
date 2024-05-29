package guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.persistence;

import java.sql.SQLException;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Cliente;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Funcionario;

public interface IMeusDadosDao {
	public Cliente buscaCliente(Cliente cliente) throws SQLException, ClassNotFoundException;
	public Funcionario buscaFuncionario (Funcionario funcionario) throws SQLException, ClassNotFoundException;
}
