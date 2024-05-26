package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class Cliente {
	
	private String nome;
	private String cpf;
	private String email;
	private LocalDate dataNascimento;
	private String endereco;
	private Acesso acesso;
}
