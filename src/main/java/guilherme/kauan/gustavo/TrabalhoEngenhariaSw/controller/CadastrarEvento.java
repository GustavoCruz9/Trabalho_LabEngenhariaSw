package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CadastrarEvento {
		
		@RequestMapping(name = "cadastrarEvento", value = "/cadastrarEvento", method = RequestMethod.GET)
		public ModelAndView cadastrarEventoGet(@RequestParam Map<String, String> param, ModelMap model) {

			return new ModelAndView("cadastrarEvento");
		}
		
		@RequestMapping(name = "cadastrarEvento", value = "/cadastrarEvento", method = RequestMethod.POST)
		public ModelAndView cadastrarEventoPost(@RequestParam Map<String, String> param, ModelMap model) {

			return new ModelAndView("cadastrarEvento");
		}
}
