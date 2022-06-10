package com.algaworks.algalog.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Problema {
	
	private int status;
	private OffsetDateTime dataHora;
	private String titulo;
	private List<Campo> campos;
	
	@Setter
	@Getter
	public static class Campo {
		private String nome;
		private String mensagem;
	}
}


