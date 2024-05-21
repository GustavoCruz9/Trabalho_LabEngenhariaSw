package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Artista;

public interface ICadastrarArtistaDao<T> {
	public String iuArtista(T artista, String acao) throws SQLException, ClassNotFoundException;
	public Artista buscarArtista(T artista)throws SQLException, ClassNotFoundException;
	public List<T> listarArtistas() throws SQLException, ClassNotFoundException;
}
