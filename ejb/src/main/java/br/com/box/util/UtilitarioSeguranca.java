package br.com.box.util;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

public final class UtilitarioSeguranca {

    private UtilitarioSeguranca() {
    }

    public static String gerarMD5(String senha) {
        Preconditions.checkNotNull(senha);
        HashCode hash = Hashing.md5().hashString(senha, Charsets.UTF_8);
        return hash.toString();
    }

    public static String gerarChaveSeguranca() {
        return gerarMD5(String.valueOf(Math.random()));
    }

    public static String getUsuarioLogado() {
        Preconditions.checkState(estaLogado());
        return getUserPrincipal().getName();
    }

    public static void login(String usuario, String senha) throws ServletException {
        FacesUtil.getServletRequest().login(usuario, gerarMD5(senha));
    }

    public static void loginToken(String usuario, String senha) throws ServletException {
        FacesUtil.getServletRequest().login(usuario, senha);
    }

    public static void logout() {
        if (estaLogado()) {
            HttpSession session = (HttpSession) FacesUtil.getExternalContext().getSession(false);
            session.invalidate();
        }
    }

    public static boolean estaLogado() {
        return getUserPrincipal() != null;
    }

    public static Principal getUserPrincipal() {
        return FacesUtil.getExternalContext().getUserPrincipal();
    }

    public static boolean verificaPapelUsuario(String papel) {
        return FacesUtil.getExternalContext().isUserInRole(papel);
    }
}