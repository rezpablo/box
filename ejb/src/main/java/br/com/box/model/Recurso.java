package br.com.box.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_recurso")
public class Recurso extends EntidadeComum implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="descricao")
	private String descricao;
	
	@Column(name="num_patrimonio")
	private String numeroPatrimonio;
	
	@ManyToOne
	@JoinColumn(name = "tipo")
	private Tipo tipo;

	@Column(name="ativo")
	private Boolean ativo;

	@OneToMany(mappedBy="recurso")
	private List<Agenda> listaAgendamento;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNumeroPatrimonio() {
		return numeroPatrimonio;
	}
	public void setNumeroPatrimonio(String numeroPatrimonio) {
		this.numeroPatrimonio = numeroPatrimonio;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public List<Agenda> getListaAgendamento() {
		return listaAgendamento;
	}
	public void setListaAgendamento(List<Agenda> listaAgendamento) {
		this.listaAgendamento = listaAgendamento;
	}
	public String getStatusAtivo() {
		if (ativo.equals(Boolean.TRUE)){
			return "Sim";
		}else{
			return "Não";
		}
		
		
	}
}
