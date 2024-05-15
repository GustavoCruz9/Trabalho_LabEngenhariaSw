package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Funcionario {
	private int registro;
	private String nome;
	private String cargo;
}