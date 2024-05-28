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

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.IndexDao;

@Controller
public class IndexController {

	@Autowired
	IndexDao iDao;
	
	@RequestMapping(name = "/", value = "/", method = RequestMethod.GET)
	public ModelAndView home(ModelMap model) {
		return indexGet(null, model);
	}

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
	public ModelAndView indexGet(@RequestParam Map<String, String> param, ModelMap model) {
		
		List<Evento> eventos = new ArrayList<>();
		String erro = "";

		try {
			eventos = buscaEventos();
			if (eventos.isEmpty()) {
				erro = "Não existem eventos cadastrados";
			}
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("eventos", eventos);
			model.addAttribute("erro", erro);
		}

		return new ModelAndView("index");
	}

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.POST)
	public ModelAndView indexPost(@RequestParam Map<String, String> param, ModelMap model) {
		
		String cmd = param.get("botao");
		String pesquisaEvento = param.get("pesquisaEvento");
		
		
		List<Evento> eventos = new ArrayList<>();
		Evento e = new Evento();
		String erro = "";

		if (cmd.contains("Pesquisar")) {
			if (pesquisaEvento.trim().isEmpty()) {
				erro = "Digite o nome de um evento";
			} else {
				e.setTitulo(pesquisaEvento);
			}
		}
		
		if (!erro.isEmpty()) {
			model.addAttribute("erro", erro);
			return new ModelAndView("index");
		}

		try {
			eventos = buscaEventosComParam(e);
			if (eventos.isEmpty()) {
				erro = "Não existem eventos com esse nome";
			}
		} catch (ClassNotFoundException | SQLException error) {
			erro = error.getMessage();

		} finally {
			model.addAttribute("eventos", eventos);
			model.addAttribute("erro", erro);
		}
		return new ModelAndView("index");

	}

	private List<Evento> buscaEventosComParam(Evento e) throws SQLException, ClassNotFoundException {
		List<Evento> eventos = new ArrayList<>();
		eventos = iDao.buscaEventosComParam(e);
		return eventos;
	}

	private List<Evento> buscaEventos() throws SQLException, ClassNotFoundException {

		List<Evento> eventos = new ArrayList<>();
		eventos = iDao.buscaEventos();
		return eventos;
	}
}