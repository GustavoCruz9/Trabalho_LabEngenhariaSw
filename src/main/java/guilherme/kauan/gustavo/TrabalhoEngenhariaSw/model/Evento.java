package guilherme.kauan.gustavo.TrabalhoEngenhariaSw.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Evento {
	private LocalTime horaInicio;
	private LocalTime horaFim;
	private LocalDate data;
	private String genero;
	private String titulo;
	private double valorBase;
	private Funcionario responsavel;
	private List<Artista> artistas = new ArrayList<>();
}
