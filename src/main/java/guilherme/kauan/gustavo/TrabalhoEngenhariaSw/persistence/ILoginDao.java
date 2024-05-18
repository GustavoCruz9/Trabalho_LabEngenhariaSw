package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.SQLException;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;

public interface ILoginDao {
	public Object[] realizaLogin(Acesso acesso) throws SQLException, ClassNotFoundException;
}
