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
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Artista;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.CadastrarArtistaDao;
import jakarta.servlet.http.HttpSession;

@Controller
public class CadastrarArtista {

	@Autowired
	CadastrarArtistaDao caDao;

	@RequestMapping(name = "cadastrarArtista", value = "/cadastrarArtista", method = RequestMethod.GET)
	public ModelAndView cadastrarArtistaGet(@RequestParam Map<String, String> param, ModelMap model, HttpSession session) {
		
		Acesso acesso = new Acesso();
	    acesso = (Acesso) session.getAttribute("acesso");
	    
		String erro = "";
	    
	    if(acesso == null || acesso.getPermissao() == 1) {
	    	erro = "Para acessar essa tela precisa estar logado";
	    	model.addAttribute("erro", erro);
	    	session.setAttribute("acesso", acesso);
	    	return new ModelAndView("index");
	    }


		return new ModelAndView("cadastrarArtista");
	}

	@RequestMapping(name = "cadastrarArtista", value = "/cadastrarArtista", method = RequestMethod.POST)
	public ModelAndView cadastrarArtistaPost(@RequestParam Map<String, String> param, ModelMap model,
			HttpSession session) {

		String cmd = param.get("botao");
		String codigo = param.get("codigo");
		String nome = param.get("nome");
		String genero = param.get("genero");

		String saida = "";
		String erro = "";

		Artista artista = new Artista();
		List<Artista> artistas = new ArrayList<>();

		Acesso acesso = new Acesso();
		acesso = (Acesso) session.getAttribute("acesso");

		if (!cmd.contains("Listar")) {
			if (cmd.contains("Cadastrar") || cmd.contains("Atualizar")) {
				if (codigo.trim().isEmpty() || nome.trim().isEmpty() || genero.trim().isEmpty()) {
					erro = "Por favor, preencha todos os campos obrigatorios.";
				}
			}
			if (cmd.contains("Buscar")) {
				if (codigo.trim().isEmpty()) {
					erro = "Para buscar insira um codigo";
				}
			}
		}

		if (!erro.isEmpty()) {
			model.addAttribute("erro", erro);
			if (acesso != null) {
				session.setAttribute("acesso", acesso);
			}
			return new ModelAndView("cadastrarArtista");
		}

		if (!cmd.contains("Listar")) {
			artista.setGenero(genero);
			artista.setNome(nome);
			artista.setCodigo(Integer.parseInt(codigo));
		}

		if (acesso != null) {
			try {
				if (cmd.contains("Cadastrar")) {
					saida = iArtista(artista);
					if(saida.contains("sucesso")) {
						artista = new Artista();
					}
				}
				if (cmd.contains("Atualizar")) {
					saida = uArtista(artista);
				}
				if (cmd.contains("Buscar")) {
					artista = buscarArtista(artista);
				}

				if (cmd.contains("Listar")) {
					artistas = listarArtistas();
					artista = new Artista();
				}

			} catch (ClassNotFoundException | SQLException e) {
				erro = e.getMessage();
			}

		} else {
			erro = "Você precisa estar logado";
		}

		model.addAttribute("saida", saida);
		model.addAttribute("erro", erro);
		model.addAttribute("artista", artista);
		model.addAttribute("artistas", artistas);
		if (acesso != null) {
			session.setAttribute("acesso", acesso);
		}

		return new ModelAndView("cadastrarArtista");
	}

	private String iArtista(Artista artista) throws SQLException, ClassNotFoundException {
		return caDao.iuArtista(artista, "I");
	}

	private String uArtista(Artista artista) throws SQLException, ClassNotFoundException {
		return caDao.iuArtista(artista, "U");
	}

	private Artista buscarArtista(Artista artista) throws SQLException, ClassNotFoundException {
		return caDao.buscarArtista(artista);
	}

	private List<Artista> listarArtistas() throws SQLException, ClassNotFoundException {
		return caDao.listarArtistas();
	}

}
