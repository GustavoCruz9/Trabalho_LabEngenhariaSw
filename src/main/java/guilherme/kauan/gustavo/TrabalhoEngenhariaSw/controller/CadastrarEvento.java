package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Artista;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.CadastrarArtistaDao;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.CadastrarEventoDao;
import jakarta.servlet.http.HttpSession;

@Controller
public class CadastrarEvento {

	@Autowired
	CadastrarEventoDao ceDao;

	@Autowired
	CadastrarArtistaDao caDao;

	@RequestMapping(name = "cadastrarEvento", value = "/cadastrarEvento", method = RequestMethod.GET)
	public ModelAndView cadastrarEventoGet(@RequestParam Map<String, String> param, ModelMap model) {

		return new ModelAndView("cadastrarEvento");
	}

	@RequestMapping(name = "cadastrarEvento", value = "/cadastrarEvento", method = RequestMethod.POST)
	public ModelAndView cadastrarEventoPost(@RequestParam Map<String, String> param, ModelMap model,
			HttpSession session, HttpSession sessionListaArtista, @ModelAttribute Evento eventoEntity) {

		@SuppressWarnings("unchecked")
		List<Artista> artistasEvento = (List<Artista>) sessionListaArtista.getAttribute("artistasEvento");
		if (artistasEvento == null) {
			artistasEvento = new ArrayList<>();
		}

		String cmd = param.get("botao");
		String codigo = param.get("codigo");
		String titulo = param.get("titulo");
		String linkImagem = param.get("linkImagem");
		String genero = param.get("genero");
		String valorBase = param.get("valorBase");
		String horaInicio = param.get("horaInicio");
		String horaFim = param.get("horaFim");
		String data = param.get("data");
		String pesquisarArtista = param.get("pesquisarArtista");
		String codigoArtista = param.get("codigoArtista");
		String nomeArtista = param.get("nomeArtista");
		String generoArtista = param.get("generoArtista");

		Artista artista = new Artista();
		List<Artista> artistas = new ArrayList<>();

		Evento evento = new Evento();
		List<Evento> eventos = new ArrayList<>();

		String saida = "";
		String erro = "";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Acesso acesso = new Acesso();
		acesso = (Acesso) session.getAttribute("acesso");

		if (!cmd.equals("Listar")) {
			if (cmd.equals("Cadastrar Artistas") || cmd.equals("Atualizar Artistas")) {
				if (codigo.trim().isEmpty() || titulo.trim().isEmpty() || linkImagem.trim().isEmpty()
						|| genero.trim().isEmpty() || valorBase.trim().isEmpty() || horaInicio.trim().isEmpty()
						|| horaFim.trim().isEmpty() || data.trim().isEmpty()) {

					erro = "Por favor, preencha todos os campos obrigatorios.";
				}
			}
			if (cmd.equals("Buscar")) {
				if (codigo.trim().isEmpty()) {
					erro = "Para buscar insira um codigo";
				} else {
					evento.setCodigo(Integer.parseInt(codigo));
				}
			}
		}

		if (!erro.isEmpty()) {
			model.addAttribute("erro", erro);
			return new ModelAndView("cadastrarEvento");
		}

		if (cmd.equals("Cadastrar Artistas") || cmd.equals("Atualizar Artistas")) {			
			evento.setCodigo(Integer.parseInt(codigo));
			evento.setTitulo(titulo);
			evento.setLinkImagem(linkImagem);
			evento.setGenero(genero);
			evento.setValor(Double.parseDouble(valorBase));
			evento.setHoraInicio(LocalTime.parse(horaInicio));
			evento.setHoraFim(LocalTime.parse(horaFim));
			evento.setData(LocalDate.parse(data, formatter));
		}
		
		try {
			if (cmd.equals("Buscar")) {
				evento = buscaEvento(evento);
			}
			if (cmd.equals("Cadastrar Artistas")) {
				sp_iEvento(evento, artistas);				
				artistas = listaArtistas();
			}
			if (cmd.equals("Atualizar Artistas")) {
				artistas = listaArtistas();
			}
			if (cmd.equals("Cadastrar")) {
				sp_iEvento(evento, artistas);
			}
			if (cmd.equals("Atualizar")) {
				 sp_uEvento(evento, artistas);
			}
			if (cmd.equals("Pesquisar")) {
				artistas = listaArtistasComParam(pesquisarArtista);
			}
			if (cmd.equals("Adicionar")) {
				artista = new Artista();
				artista.setCodigo(Integer.parseInt(codigoArtista));
				artista.setNome(nomeArtista);
				artista.setGenero(generoArtista);
				artistasEvento.add(artista);
				artistas = listaArtistas();
			}
			if (cmd.equals("Excluir")) {
				artistasEvento.remove(0);
			}
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("artista", artista);
			model.addAttribute("artistas", artistas);
			model.addAttribute("evento", evento);
			model.addAttribute("evento", eventoEntity);
			if (acesso != null) {
				session.setAttribute("acesso", acesso);
			}
			if (!artistasEvento.isEmpty()) {
				sessionListaArtista.setAttribute("artistasEvento", artistasEvento);
			}
		}

		return new ModelAndView("cadastrarEvento");
	}

	private List<Artista> listaArtistasComParam(String pesquisarArtista) {
		return ceDao.listaArtistasComParam(pesquisarArtista);
	}

	private Evento buscaEvento(Evento evento) throws SQLException, ClassNotFoundException {
		return ceDao.buscaEvento(evento);
	}

	private void sp_uEvento(Evento evento, List<Artista> artistas) throws SQLException, ClassNotFoundException {
		ceDao.sp_iuEvento("U", evento, artistas);
	}

	private void sp_iEvento(Evento evento, List<Artista> artistas) throws SQLException, ClassNotFoundException {
		 ceDao.sp_iuEvento("I", evento, artistas);
	}

	private List<Artista> listaArtistas() throws SQLException, ClassNotFoundException {
		return caDao.listarArtistas();
	}
}
