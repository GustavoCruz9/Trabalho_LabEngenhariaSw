package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Evento;

@Controller
public class IndexController {

	@RequestMapping(name = "/", value = "/", method = RequestMethod.GET)
	public ModelAndView home(ModelMap model) {
		return indexGet(null, model);
	}

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
	public ModelAndView indexGet(@RequestParam Map<String, String> param, ModelMap model) {

		ArrayList<Evento> eventos = new ArrayList<>();

		for (int i = 0; i < 9; i++) {

			Evento e = new Evento();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate data = LocalDate.parse("22-04-2004", formatter);
			e.setData(data);

			e.setTitulo("Artista " + i);
			e.setLinkImagem("https://bn02pap001files.storage.live.com/y4mcAOkgx_aGs9KCfo-H"
					+ "CfsnbKl2bijtm029GIV7LMgDi6vsI4tjgMrdkoAlmZ6O6xpZvgbVUPDHrwicahCpzUhR-"
					+ "5af31nz_oMO4pOgjEqYPePPKPUTToxYa7W4UTkbN67ZHSecyKiRgUKJPZkfW6FxLGZ3KW"
					+ "c4HoDrQnhPkmTaVlO5SB6HwBxhFv5BMS4EQXlPTJ28trd7z3EHNxTOks-yLzOdfFqgfgJ"
					+ "LC84ZU3ymkM?encodeFailures=1&width=640&height=640");

			eventos.add(e);
		}

		model.addAttribute("eventos", eventos);
		return new ModelAndView("index");
	}

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.POST)
	public ModelAndView indexPost(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("index");
	}
}