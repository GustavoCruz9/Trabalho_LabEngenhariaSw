package guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.persistence;

import java.sql.SQLException;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Acesso;

public interface ILoginDao {
	public Object[] realizaLogin(Acesso acesso) throws SQLException, ClassNotFoundException;
}
