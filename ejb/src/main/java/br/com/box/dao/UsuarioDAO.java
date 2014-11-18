package br.com.box.dao;

import javax.ejb.Stateless;

import br.com.box.model.Usuario;

@Stateless
public class UsuarioDAO extends GenericDAO<Usuario> {
	
	public Usuario buscarPorLogin(String login){
		return buscarPor("login", login);
	}
	
}


