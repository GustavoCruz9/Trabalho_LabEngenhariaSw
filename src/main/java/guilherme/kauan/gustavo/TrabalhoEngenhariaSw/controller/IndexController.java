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

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
	public ModelAndView indexGet(@RequestParam Map<String, String> param, ModelMap model) {
		
		ArrayList<Evento> eventos = new ArrayList<>();
		
		for(int i = 0; i < 9; i++) {
			
			Evento e = new Evento();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate data = LocalDate.parse("22-04-2004", formatter);
			e.setData(data);
			
			e.setTitulo("Artista " + i);
			
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