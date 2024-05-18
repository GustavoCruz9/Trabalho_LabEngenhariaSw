package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Acesso;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Cliente;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.AtualizarCadastrarClienteDao;

@Controller
public class AtualizarCadastrarClienteController {

	@Autowired
	AtualizarCadastrarClienteDao accDao;

	@RequestMapping(name = "cadastrarCliente", value = "/cadastrarCliente", method = RequestMethod.GET)
	public ModelAndView CadastrarClienteGet(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("cadastrarCliente");
	}

	@RequestMapping(name = "cadastrarCliente", value = "/cadastrarCliente", method = RequestMethod.POST)
	public ModelAndView CadastrarClientePost(@RequestParam Map<String, String> param, ModelMap model) {

		String cmd = param.get("botao");
		String nome = param.get("nome");
		String cpf = param.get("cpf");
		String email = param.get("email");
		String dataNascimento = param.get("dataNascimento");
		String endereco = param.get("endereco");
		String usuario = param.get("usuario");
		String senha = param.get("senha");
		String confirmarSenha = param.get("confirmarSenha");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String saida = "";
		String erro = "";

		if (cmd.contains("Cadastrar")) {
			if (nome.trim().isEmpty() || cpf.trim().isEmpty() || email.trim().isEmpty()
					|| dataNascimento.trim().isEmpty() || endereco.trim().isEmpty() || usuario.trim().isEmpty()
					|| senha.trim().isEmpty() || confirmarSenha.trim().isEmpty()) {

				erro = "Por favor, preencha todos os campos obrigatorios.";
				model.addAttribute("erro", erro);
				return new ModelAndView("cadastrarCliente");
			}
		}

		Cliente c = new Cliente();
		Acesso a = new Acesso();
		
		if(cmd.contains("Cadastrar")) {
			c.setNome(nome);
			c.setCpf(cpf);
			c.setEmail(email);
			c.setEndereco(endereco);

			LocalDate dataNascimentoLocalDate = LocalDate.parse(dataNascimento, formatter);
			c.setDataNascimento(dataNascimentoLocalDate);

			a.setSenha(senha);
			a.setUsuario(usuario);
			c.setAcesso(a);
		}

		if (!senha.equals(confirmarSenha)) {
			erro = "As senhas nao coincidem, insira novamente";
			model.addAttribute("erro", erro);
			model.addAttribute("cliente", c);
			return new ModelAndView("cadastrarCliente");
		}
		

		try {
			saida = iuCliente(c);
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			if(!erro.isEmpty()) {
				model.addAttribute("cliente", c);
				model.addAttribute("confirmarSenha", confirmarSenha);
			}
		}

		return new ModelAndView("cadastrarCliente");

	}

	private String iuCliente(Cliente c) throws SQLException, ClassNotFoundException {
		return accDao.iuCliente("I", c);
	}

}
