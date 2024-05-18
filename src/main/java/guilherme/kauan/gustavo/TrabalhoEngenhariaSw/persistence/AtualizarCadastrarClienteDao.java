package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Cliente;

@Repository
public class AtualizarCadastrarClienteDao implements IAtualizarCadastrarClienteDao {
	
	@Autowired
	GenericDao gDao;

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
