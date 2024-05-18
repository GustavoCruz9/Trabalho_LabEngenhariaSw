package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Cliente;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Funcionario;

@Repository
public class MeusDadosDao implements IMeusDadosDao, IAtualizarCadastrarClienteDao {
	
	@Autowired
	GenericDao gDao;

	public Cliente buscaCliente(Cliente cliente) throws SQLException, ClassNotFoundException {
		
		Cliente cliente2 = new Cliente();
		Connection c = gDao.getConnection();
		String sql = "Select * from Cliente where loginUsuario = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cliente.getAcesso().getUsuario());

		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			
			cliente2.setCpf(rs.getString("cpf"));
			cliente2.setNome(rs.getString("nome"));
			cliente2.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
			cliente2.setEmail(rs.getString("email"));
			cliente2.setEndereco(rs.getString("endereco"));
			cliente2.setAcesso(cliente.getAcesso());
			
		}
		
		ps.close();
		c.close();

		return cliente2;
	}

	@Override
	public Funcionario buscaFuncionario(Funcionario funcionario) throws SQLException, ClassNotFoundException {
		Funcionario funcionario2 = new Funcionario();
		Connection c = gDao.getConnection();
		String sql = "Select * from Funcionario where loginUsuario = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, funcionario.getAcesso().getUsuario());

		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {

			funcionario2.setRegistro(rs.getString("registro"));
			funcionario2.setNome(rs.getString("nome"));
			funcionario2.setCargo(rs.getString("cargo"));
			funcionario2.setAcesso(funcionario.getAcesso());
			
		}
		
		ps.close();
		c.close();

		return funcionario2;
	}

	@Override
	public String iuCliente(String acao, Cliente c) throws SQLException, ClassNotFoundException {
		
		Connection C = gDao.getConnection();
		String sql = "{CALL sp_iuCliente (?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		CallableStatement cs = C.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setString(2, c.getCpf());
		cs.setString(3, c.getNome());
		cs.setString(4, c.getDataNascimento().toString());
		cs.setString(5, c.getEmail());
		cs.setString(6, c.getEndereco());
		cs.setString(7, c.getAcesso().getUsuario());
		cs.setString(8, c.getAcesso().getSenha());
		
		cs.registerOutParameter(9, Types.VARCHAR);
		
		cs.execute();
		
		String saida = cs.getString(9);
		
		cs.close();
		C.close();
		return saida;
	}

}
