package com.algaworks.algalog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algalog.domain.exception.NegotiationException;
import com.algaworks.algalog.domain.validadtion.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)
	private List<Ocorrencia> ocorrencias =
			new ArrayList<>(1);
	@Valid
	@NotNull
	@Embedded
	private Destinatario destinatario;
	
	@NotNull
	@Column
	private BigDecimal taxa;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private StatusEntrega status;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column
	private OffsetDateTime dataPedido;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Column
	private OffsetDateTime dataFinalizacao;

	public Ocorrencia addOcorrencia(String desc) { //negotiation method
		var ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(desc);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		
		this.ocorrencias.add(ocorrencia);
		return ocorrencia;
	}
	
	public void finalizar() { //negotiation method
		if(status.equals(StatusEntrega.PENDENTE)) { //if Entrega is PENDENTE
			status = StatusEntrega.FINALIZADA;
			dataFinalizacao = OffsetDateTime.now();
		}else { //if Entrega is not PENDENTE
			throw new NegotiationException(
				"A entrega não pôde ser finalizada!");
		}

	}
	
}
