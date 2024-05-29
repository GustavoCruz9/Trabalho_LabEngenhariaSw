package guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.persistence;

import java.sql.SQLException;
import java.util.List;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Artista;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSoftware.model.Evento;

public interface ICadastrarEventoDao {
	public String sp_iuEvento(String acao, Evento evento) throws SQLException, ClassNotFoundException;
	public Evento buscaEvento(Evento evento) throws SQLException, ClassNotFoundException;
	public List<Artista> listaArtistasComParam(String pesquisarArtista) throws SQLException, ClassNotFoundException;
}
