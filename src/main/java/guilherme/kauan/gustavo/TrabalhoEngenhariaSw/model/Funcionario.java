package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Funcionario {
	private String registro;
	private String nome;
	private String cargo;
	private Acesso acesso;
}