package br.com.box.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tb_pessoa")
public class Pessoa extends EntidadeComum implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(nullable=false)
	private String nome;
	
	@Column(length=11, nullable=false, unique=true)
	private String cpf;
	
	@Column(name="data_nasc", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	private String email;
	
	//Getters and Setters
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
