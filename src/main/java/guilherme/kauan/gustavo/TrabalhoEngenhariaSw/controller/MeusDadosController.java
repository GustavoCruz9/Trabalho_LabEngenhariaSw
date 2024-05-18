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
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model.Funcionario;
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.MeusDadosDao;
import jakarta.servlet.http.HttpSession;

@Controller
public class MeusDadosController {

	@Autowired
	MeusDadosDao mdDao;

	@RequestMapping(name = "meusDados", value = "/meusDados", method = RequestMethod.GET)
	public ModelAndView MeusDadosGet(@RequestParam Map<String, String> param, ModelMap model, HttpSession session) {

		Acesso acesso = new Acesso();
		Cliente cliente = new Cliente();
		Funcionario funcionario = new Funcionario();

		String erro = "";

		acesso = (Acesso) session.getAttribute("acesso");

		try {
			if (acesso.getPermissao() == 1) {
				cliente.setAcesso(acesso);
				cliente = buscaCliente(cliente);
			}

			if (acesso.getPermissao() == 2) {
				funcionario.setAcesso(acesso);
				funcionario = buscaFuncionario(funcionario);
			}
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("cliente", cliente);
			model.addAttribute("funcionario", funcionario);
			model.addAttribute("erro", erro);
			model.addAttribute("acesso", acesso);

		}

		return new ModelAndView("meusDados");
	}

	@RequestMapping(name = "meusDados", value = "/meusDados", method = RequestMethod.POST)
	public ModelAndView MeusDadoPost(@RequestParam Map<String, String> param, ModelMap model, HttpSession session) {
		
		String cmd = param.get("botao");
		String nome = param.get("nome");
		String cpf = param.get("cpf");
		String email = param.get("email");
		String dataNascimento = param.get("dataNascimento");
		String endereco = param.get("endereco");
		
		Acesso acesso = new Acesso();
		acesso = (Acesso) session.getAttribute("acesso");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String saida = "";
		String erro = "";
		
		if (cmd.contains("Atualizar")) {
			if (nome.trim().isEmpty() ||email.trim().isEmpty() || endereco.trim().isEmpty()) {
				erro = "Por favor, preencha todos os campos obrigatorios.";
				Cliente cliente = new Cliente();
				cliente.setAcesso(acesso);
				try {
					cliente = buscaCliente(cliente);
				} catch (ClassNotFoundException | SQLException e) {
					erro = e.getMessage();
				}finally {
					model.addAttribute("erro", erro);
					model.addAttribute("acesso", acesso);
					model.addAttribute("cliente", cliente);
				}
				return new ModelAndView("meusDados");
			}
		}
		
		Cliente c = new Cliente();
		Acesso a = new Acesso();
		
		if(cmd.contains("Atualizar")) {
			c.setNome(nome);
			c.setCpf(cpf);
			c.setEmail(email);
			c.setEndereco(endereco);

			LocalDate dataNascimentoLocalDate = LocalDate.parse(dataNascimento, formatter);
			c.setDataNascimento(dataNascimentoLocalDate);

			a.setSenha(acesso.getSenha());
			a.setUsuario(acesso.getUsuario());
			c.setAcesso(a);
		}
		
		try {
			saida = atualizaCliente(c);
			c = buscaCliente(c);
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("acesso", acesso);
			model.addAttribute("cliente", c);
		}

		return new ModelAndView("meusDados");
	}

	private String atualizaCliente(Cliente c) throws ClassNotFoundException, SQLException {
		return mdDao.iuCliente("U", c) ;
	}

	private Cliente buscaCliente(Cliente cliente) throws SQLException, ClassNotFoundException {
		return mdDao.buscaCliente(cliente);
	}

	private Funcionario buscaFuncionario(Funcionario funcionario) throws SQLException, ClassNotFoundException {
		return mdDao.buscaFuncionario(funcionario);
	}

}
