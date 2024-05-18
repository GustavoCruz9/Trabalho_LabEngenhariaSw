package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Acesso {
	 private String usuario;
	 private String senha;
	 private int permissao;
}
