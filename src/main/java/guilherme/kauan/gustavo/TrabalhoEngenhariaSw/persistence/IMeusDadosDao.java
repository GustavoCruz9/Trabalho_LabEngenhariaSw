package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.SQLException;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Cliente;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Funcionario;

public interface IMeusDadosDao {
	public Cliente buscaCliente(Cliente cliente) throws SQLException, ClassNotFoundException;
	public Funcionario buscaFuncionario (Funcionario funcionario) throws SQLException, ClassNotFoundException;
}
