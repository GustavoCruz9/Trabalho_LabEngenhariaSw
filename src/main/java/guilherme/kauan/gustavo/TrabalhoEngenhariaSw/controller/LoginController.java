package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.LoginDao;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	LoginDao lDao;
	
	@RequestMapping(name = "login", value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("login");
	}
	
	@RequestMapping(name = "login", value = "/login", method = RequestMethod.POST)
	public ModelAndView loginPost(@RequestParam Map<String, String> param, HttpSession session, ModelMap model) {
		String cmd = param.get("botao");
		String usuario = param.get("usuario");
		String senha = param.get("senha");
		String erro = "";
		String saida = "";
		Acesso acesso = new Acesso();
		
		if(cmd.equals("Entrar")) {
			if(usuario.trim().isEmpty() || senha.trim().isEmpty()) {
				erro = "Por favor, preencha todos os campos obrigatorios.";
				model.addAttribute("erro", erro);
				return new ModelAndView("cadastrarCliente");
			}
			acesso.setUsuario(usuario);
			acesso.setSenha(senha);
		}
		
		
		try {
			if(cmd.equals("Entrar")) {
				Object saidas[] = fazerLogin(acesso);
				saida = (String) saidas[0];
				acesso = (Acesso) saidas[1];
				if(!saida.equals("Logou")) {
					acesso = null;
				}			
			}
			
			if(cmd.equals("Deslogar")) {
				session = deslogar(session);
				erro = "Você deslogou, redirecionando para página principal...";
				model.addAttribute("erro", erro);
				return new ModelAndView("index");
			}
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			if(cmd.equals("Entrar")) {
				if(acesso != null) {
					session.setAttribute("acesso", acesso);
					return new ModelAndView(new RedirectView("index"));
				}
			}
		}
		return new ModelAndView("login");
	}

	private HttpSession deslogar(HttpSession session) {
		session.invalidate();
		return session;
	}

	private Object[] fazerLogin(Acesso acesso) throws ClassNotFoundException, SQLException {
		return lDao.realizaLogin(acesso);
	}
	
	
}
