package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Artista;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;

@Repository
public class CadastrarEventoDao implements ICadastrarEventoDao {
	
	@Autowired
	GenericDao gDao;

	@Override
	public void sp_iuEvento(String acao, Evento evento, List<Artista> artistas) throws SQLException, ClassNotFoundException {
	}

	@Override
	public Evento buscaEvento(Evento evento) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Artista> listaArtistasComParam(String pesquisarArtista) {
		// TODO Auto-generated method stub
		return null;
	}


}
