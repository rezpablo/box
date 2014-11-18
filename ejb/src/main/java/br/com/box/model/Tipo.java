package br.com.box.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_tipo")
public class Tipo extends EntidadeComum implements Serializable{

	private static final long serialVersionUID = 1L;

	
	private String nome;
	private String cor;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
}
