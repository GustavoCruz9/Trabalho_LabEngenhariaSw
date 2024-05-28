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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Artista;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Funcionario;
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
	public ModelAndView cadastrarEventoGet(@RequestParam Map<String, String> param, ModelMap model, HttpSession session,
			HttpSession sessionListaArtista) {
		
		Acesso acesso = new Acesso();
	    acesso = (Acesso) session.getAttribute("acesso");
	    String erro = "";
		
		if(acesso == null || acesso.getPermissao() == 1) {
	    	erro = "Para acessar essa tela precisa estar logado";
	    	model.addAttribute("erro", erro);
	    	session.setAttribute("acesso", acesso);
	    	return new ModelAndView("index");
	    }
		
		@SuppressWarnings("unchecked")
		List<Artista> artistasEvento = (List<Artista>) sessionListaArtista.getAttribute("artistasEvento");
		artistasEvento = new ArrayList<>();
		
		String codEvento = param.get("codEvento");		

		List<Artista> artistas = new ArrayList<>();
		Evento evento = new Evento();

		try {
			artistas = listaArtistas();
			if (codEvento != null) {
				evento.setCodigo(Integer.parseInt(codEvento));
				evento = buscaEvento(evento);
				artistasEvento = evento.getArtistas();
			}
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("artistas", artistas);
			sessionListaArtista.setAttribute("artistasEvento", artistasEvento);
			if (codEvento != null) {
				model.addAttribute("evento", evento);
			}
		}

		return new ModelAndView("cadastrarEvento");
	}

	@RequestMapping(name = "cadastrarEvento", value = "/cadastrarEvento", method = RequestMethod.POST)
	public ModelAndView cadastrarEventoPost(@RequestParam Map<String, String> param, ModelMap model,
			HttpSession session, HttpSession sessionListaArtista) {

		@SuppressWarnings("unchecked")
		List<Artista> artistasEvento = (List<Artista>) sessionListaArtista.getAttribute("artistasEvento");
		if (artistasEvento == null) {
			artistasEvento = new ArrayList<>();
		}

		String cmd = param.get("botao");
		String codigo = param.get("codigo");
		String titulo = param.get("titulo");
		String linkImagem = param.get("linkImagem");
		String valorBase = param.get("valorBase");
		String horaInicio = param.get("horaInicio");
		String horaFim = param.get("horaFim");
		String data = param.get("data");
		String pesquisarArtista = param.get("pesquisarArtista");
		String codigoArtista = param.get("codigoArtista");
		String nomeArtista = param.get("nomeArtista");
		String generoArtista = param.get("generoArtista");
		String statusEvento = param.get("statusEvento");

		Artista artista = new Artista();
		List<Artista> artistas = new ArrayList<>();

		Evento evento = new Evento();
		List<Evento> eventos = new ArrayList<>();

		Funcionario funcionario = new Funcionario();
		
		String saida = "";
		String erro = "";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Acesso acesso = new Acesso();
		acesso = (Acesso) session.getAttribute("acesso");

		if (cmd.equals("Cadastrar") || cmd.equals("Atualizar") 
			||  cmd.equals("Adicionar") ||  cmd.equals("Remover") ) {
			if (codigo.trim().isEmpty() || titulo.trim().isEmpty() || linkImagem.trim().isEmpty()
					|| valorBase.trim().isEmpty() || horaInicio.trim().isEmpty() || horaFim.trim().isEmpty()
					|| data.trim().isEmpty()) {

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

		if (cmd.equals("Cadastrar") || cmd.equals("Atualizar")) {
			if (artistasEvento.isEmpty()) {
				erro = "Um evento deve possuir pelo menos um artista";
			}
		}
		

		if (!erro.isEmpty()) {
			try {
				artistas = listaArtistas();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			model.addAttribute("erro", erro);
			model.addAttribute("artista", artista);
			model.addAttribute("artistas", artistas);
			model.addAttribute("evento", evento);
			if (acesso != null) {
				session.setAttribute("acesso", acesso);
			}
			if (!artistasEvento.isEmpty()) {
				sessionListaArtista.setAttribute("artistasEvento", artistasEvento);
			}
			return new ModelAndView("cadastrarEvento");
		}
		
		if(!cmd.equals("Buscar")) {
			evento.setCodigo(Integer.parseInt(codigo));
			evento.setTitulo(titulo);
			evento.setLinkImagem(linkImagem);
			evento.setValor(Double.parseDouble(valorBase));
			evento.setHoraInicio(LocalTime.parse(horaInicio));
			evento.setHoraFim(LocalTime.parse(horaFim));
			evento.setData(LocalDate.parse(data, formatter));
			if(statusEvento != null) {
				evento.setStatusEvento(statusEvento);
			}
		}
		

		try {
			if (cmd.equals("Buscar")) {
				evento = buscaEvento(evento);
				artistasEvento = evento.getArtistas();
				artistas = listaArtistas();
				
			}
			if (cmd.equals("Pesquisar")) {
				artistas = listaArtistasComParam(pesquisarArtista);
			}
			if (cmd.equals("Adicionar")) {

				boolean status = true;

				artista = new Artista();
				artista.setCodigo(Integer.parseInt(codigoArtista));
				artista.setNome(nomeArtista);
				artista.setGenero(generoArtista);
				for (Artista a : artistasEvento) {
					if (artista.getCodigo() == a.getCodigo()) {
						erro = "Este artista ja esta inserido no evento";
						status = false;
					}
				}
				if (status == true) {
					artistasEvento.add(artista);
				}
				artistas = listaArtistas();
			}
			if (cmd.equals("Remover")) {
				artista = new Artista();
				artista.setCodigo(Integer.parseInt(codigoArtista));
				for (int i = 0; i < artistasEvento.size(); i++) {
					if (artista.getCodigo() == artistasEvento.get(i).getCodigo()) {
						artistasEvento.remove(i);
					}
				}
				artistas = listaArtistas();
			}

			if (cmd.equals("Cadastrar")) {
				if(!statusEvento.equalsIgnoreCase("Inativo")) {
					
					funcionario.setAcesso(acesso);
					evento.setResponsavel(funcionario);
					evento.setArtistas(artistasEvento);
					
					evento.setGenero(insereGenero(artistasEvento));
					
					saida = sp_iEvento(evento);
					
					sessionListaArtista.removeAttribute("artistasEvento");
					artistasEvento = new ArrayList<>();
					evento = new Evento();
					artistas = new ArrayList<>();
				}else {
					erro = "Não é possivel cadastrar um evento com status inativo";
				}
			}

			if (cmd.equals("Atualizar")) {
				
				funcionario.setAcesso(acesso);
				evento.setResponsavel(funcionario);				
				evento.setArtistas(artistasEvento);
				evento.setGenero(insereGenero(artistasEvento));
				
				saida = sp_uEvento(evento);
				
				sessionListaArtista.removeAttribute("artistasEvento");
				artistasEvento = new ArrayList<>();
				evento = new Evento();
				artistas = new ArrayList<>();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("artista", artista);
			model.addAttribute("artistas", artistas);
			model.addAttribute("evento", evento);
			if (acesso != null) {
				session.setAttribute("acesso", acesso);
			}
			if (!artistasEvento.isEmpty()) {
				sessionListaArtista.setAttribute("artistasEvento", artistasEvento);
			}
		}

		return new ModelAndView("cadastrarEvento");
	}

	private String insereGenero(List<Artista> artistasEvento) {
		String generosEvento = "";
		boolean status = true;

		List<String> generos = new ArrayList<>();

		for (int i = 0; i < artistasEvento.size(); i++) {
			for (int j = 0; j < generos.size(); j++) {	
				if (artistasEvento.get(i).getGenero().equalsIgnoreCase(generos.get(j))) {
					status = false;
					break;
				} else {
					status = true;
				}
			}
			if (status == true) {
				generos.add(artistasEvento.get(i).getGenero());
			}
		}

		for (int i = 0; i < generos.size(); i++) {
			if (generos.size() - 1 == i) {
				generosEvento += generos.get(i);
			} else {
				generosEvento += generos.get(i) + ", ";
			}
		}
		return generosEvento;
	}

	private List<Artista> listaArtistasComParam(String pesquisarArtista) throws SQLException, ClassNotFoundException {
		return ceDao.listaArtistasComParam(pesquisarArtista);
	}

	private Evento buscaEvento(Evento evento) throws SQLException, ClassNotFoundException {
		return ceDao.buscaEvento(evento);
	}

	private String sp_uEvento(Evento evento) throws SQLException, ClassNotFoundException {
		return ceDao.sp_iuEvento("U", evento);
	}

	private String sp_iEvento(Evento evento) throws SQLException, ClassNotFoundException {
		return ceDao.sp_iuEvento("I", evento);
	}

	private List<Artista> listaArtistas() throws SQLException, ClassNotFoundException {
		return caDao.listarArtistas();
	}
}
