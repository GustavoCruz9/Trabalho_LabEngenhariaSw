package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;

@Repository
public class LoginDao implements ILoginDao {

	@Autowired
	GenericDao gDao;

	@Override
	public Object[] realizaLogin(Acesso acesso) throws SQLException, ClassNotFoundException {
		Object[] saidas = new Object[2];

		Connection C = gDao.getConnection();
		String sql = "{CALL sp_realizaLogin (?, ?, ?, ?)}";
		CallableStatement cs = C.prepareCall(sql);
		cs.setString(1, acesso.getUsuario());
		cs.setString(2, acesso.getSenha());
		cs.registerOutParameter(3, Types.VARCHAR);
		cs.registerOutParameter(4, Types.INTEGER);
		
		cs.execute();
//		ResultSet rs = cs.getResultSet();
		String saida = cs.getString(3);
		int permissao = cs.getInt(4);
		
		if(permissao != 0) {
			acesso.setPermissao(permissao);
		}
		
//		if (rs.next()) {
//			acesso.setPermissao(rs.getInt("permissao"));
//		}

		cs.close();
//		rs.close();
		C.close();

		saidas[0] = saida;
		saidas[1] = acesso;

		return saidas;
	}

}
