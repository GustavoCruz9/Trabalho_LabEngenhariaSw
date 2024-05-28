package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.controller;

import java.sql.SQLException;
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
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Ingresso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.VisualizarIngressosDao;
import jakarta.servlet.http.HttpSession;

@Controller
public class visualizarIngressosController {
	
	@Autowired
	VisualizarIngressosDao viDao;
	
	@RequestMapping(name = "visualizarIngressos", value = "/visualizarIngressos", method = RequestMethod.GET)
	public ModelAndView visualizarIngressosGet(@RequestParam Map<String, String> param, ModelMap model, HttpSession session) {
		
		List<Evento> eventos = new ArrayList<>();
		String erro = "";
		String saida = "";
		
		Acesso acesso = new Acesso();
		acesso = (Acesso) session.getAttribute("acesso");
		
		if(acesso == null) {
	    	erro = "Para acessar essa tela precisa estar logado";
	    	model.addAttribute("erro", erro);
	    	session.setAttribute("acesso", acesso);
	    	return new ModelAndView("index");
	    }

		try {
			eventos = buscaEvento(acesso);
			if(eventos.isEmpty()) {
				saida = "Você não possui ingressos disponíveis no momento.";
			}
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("eventos", eventos);
			if (acesso != null) {
				session.setAttribute("acesso", acesso);
			}
		}
		
		return new ModelAndView("visualizarIngressos");
	}

	@RequestMapping(name = "visualizarIngressos", value = "/visualizarIngressos", method = RequestMethod.POST)
	public ModelAndView visualizarIngressosPost(@RequestParam Map<String, String> param, ModelMap model, HttpSession session) {
		
		Acesso acesso = new Acesso();
		acesso = (Acesso) session.getAttribute("acesso");

		String erro = "";
		String saida = "";
		
		String eventoCodigo = param.get("eventoCodigo");
		String codigoIngresso = param.get("codigoIngresso");
		String cmd = param.get("botao");
		
		Evento evento = new Evento();
		Ingresso ingresso = new Ingresso();
		
		if(eventoCodigo != null && !eventoCodigo.trim().isEmpty()) {
			evento.setCodigo(Integer.parseInt(eventoCodigo));
		}
		
		if(cmd != null && cmd.equalsIgnoreCase("Cancelar")) {
			ingresso.setCodigo(Integer.parseInt(codigoIngresso));
		}
		
		List<Ingresso> ingressos = new ArrayList<>();
		List<Evento> eventos = new ArrayList<>();
		
		try {
			if(cmd != null && cmd.equalsIgnoreCase("Cancelar")) {
				saida = cancelarIngresso(ingresso);
			}else {
				ingressos = buscaIngressos(evento, acesso);
			}
			eventos = buscaEvento(acesso);
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}finally {
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("ingressos", ingressos);
			model.addAttribute("eventos", eventos);
			if (acesso != null) {
				session.setAttribute("acesso", acesso);
			}
		}
		
		return new ModelAndView("visualizarIngressos");
	}
	
	private String cancelarIngresso(Ingresso ingresso) throws SQLException, ClassNotFoundException {
		return viDao.cancelarIngresso(ingresso);
	}

	private List<Ingresso> buscaIngressos(Evento evento, Acesso acesso) throws SQLException, ClassNotFoundException {
		return viDao.buscaIngressos(evento, acesso);
	}

	private List<Evento> buscaEvento(Acesso acesso) throws SQLException, ClassNotFoundException {
		return viDao.buscaEvento(acesso);
	}
}
