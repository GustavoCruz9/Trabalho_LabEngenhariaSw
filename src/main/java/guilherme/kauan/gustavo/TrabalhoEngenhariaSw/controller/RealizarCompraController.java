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
import guilherme.kauan.gustavo.TrabalhoEngenhariaSw.persistence.RealizarCompraDao;
import jakarta.servlet.http.HttpSession;

@Controller
public class RealizarCompraController {

	@Autowired
	RealizarCompraDao rcDao;

	@RequestMapping(name = "realizarCompra", value = "/realizarCompra", method = RequestMethod.GET)
	public ModelAndView realizarCompraGet(@RequestParam Map<String, String> param, ModelMap model, HttpSession session) {
		
		Acesso acesso = new Acesso();
	    acesso = (Acesso) session.getAttribute("acesso");
	    
		String erro = "";
	    
	    if(acesso != null && acesso.getPermissao() == 2) {
	    	erro = "Funcionário não pode realizar compra de ingressos";
	    	model.addAttribute("erro", erro);
	    	session.setAttribute("acesso", acesso);
	    	return new ModelAndView("index");
	    }

		String codEvento = param.get("codEvento");



		Evento evento = new Evento();
		evento.setCodigo(Integer.parseInt(codEvento));

		try {
			evento = buscarEvento(evento);
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("evento", evento);
		}

		return new ModelAndView("realizarCompra");
	}

	@RequestMapping(name = "realizarCompra", value = "/realizarCompra", method = RequestMethod.POST)
	public ModelAndView realizarCompraPost(@RequestParam Map<String, String> param, ModelMap model, HttpSession sessionIngressos, HttpSession session) {


		@SuppressWarnings("unchecked")
		List<Ingresso> ingressos = (List<Ingresso>) sessionIngressos.getAttribute("ingressos");
	    if (ingressos == null) {
	        ingressos = new ArrayList<>();
	    }
	    
	    Acesso acesso = new Acesso();
	    acesso = (Acesso) session.getAttribute("acesso");
	    
		String tipo = param.get("tipo");
		String setor = param.get("setor");
		String codEvento = param.get("codEvento");
		String quantidade = param.get("quantidade");
		String cmd = param.get("botao");

		Ingresso ingresso = new Ingresso();
		ingresso.setSetor(setor);
		ingresso.setTipo(tipo);

		String erro = "";
		String saida = "";

		Evento evento = new Evento();
		evento.setCodigo(Integer.parseInt(codEvento));

		try {
			evento = buscarEvento(evento);
			if (tipo != null && setor != null) {
				if (tipo.equals("inteira") || tipo.equals("meia")) {
					switch (setor) {
					case ("Pista"):
						ingresso.setPreco(evento.getValor());
						break;
					case ("FrontStage"):
						ingresso.setPreco(evento.getValor() * 2);
						break;
					case ("Camarote"):
						ingresso.setPreco(evento.getValor() * 3);
						break;
					default:
						ingresso.setPreco(0.0);
					}
				}

				if (tipo.equals("meia")) {
					ingresso.setPreco(ingresso.getPreco() / 2);
				}
			}

			if(cmd != null) {
				if (cmd.equals("Adicionar ao carrinho") && tipo != null && setor != null) {
					int i = 0;
					ingresso.setEvento(evento);
					while (i < Integer.parseInt(quantidade)) {
						ingressos.add(ingresso);
						i++;
					}
					if(ingressos.size() > 4) {
						erro = "O limite de ingressos foi atingido";
						while(ingressos.size() != 4) {
							ingressos.remove(ingressos.size() - 1);
						}
					}
				}		
				if(cmd.equals("Limpar carrinho")) {
					sessionIngressos.removeAttribute("ingressos");
					ingressos = new ArrayList<>();
					ingresso = new Ingresso();
					return new ModelAndView("realizarCompra");		
				}
				if(cmd.equals("Finalizar compra")) {
					if(acesso != null){
						if(!ingressos.isEmpty()) {
							saida = finalizarCompra(ingressos, acesso);
							ingresso = new Ingresso();
							sessionIngressos.removeAttribute("ingressos");
							ingressos = new ArrayList<>();			
						}else {
							erro = "Você precisa adicionar ao carrinho";
							model.addAttribute("erro", erro);	
						}
					}else {
						erro = "Você precisa esta Logado";
						model.addAttribute("erro", erro);						
					}
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			if(!ingressos.isEmpty()) {
				sessionIngressos.setAttribute("ingressos", ingressos);				
			}
			model.addAttribute("erro", erro);
			if(session != null) {
				session.setAttribute("acesso", acesso);
			}
			model.addAttribute("saida", saida);
			model.addAttribute("evento", evento);
			model.addAttribute("ingresso", ingresso);
		}

		return new ModelAndView("realizarCompra");
	}

	private String finalizarCompra(List<Ingresso> ingressos, Acesso acesso) throws ClassNotFoundException, SQLException {
		return rcDao.finalizarCompra(ingressos, acesso);
	}

	private Evento buscarEvento(Evento evento) throws SQLException, ClassNotFoundException {
		return rcDao.buscarEvento(evento);
	}
}
