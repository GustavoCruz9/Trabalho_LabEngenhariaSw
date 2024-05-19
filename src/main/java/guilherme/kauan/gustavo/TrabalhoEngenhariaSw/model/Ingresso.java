package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Ingresso {
     private int codigo;
	 private double preco;
	 private String tipo;
	 private String setor;
	 private Evento evento;
}
