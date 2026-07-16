package com.adridevelop.journalytic_backend.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adridevelop.journalytic_backend.exceptions.UsuarioNoEncontradoException;
import com.adridevelop.journalytic_backend.models.dao.UsuarioDAO;

@Service
public class JWTUserDetailsService implements UserDetailsService{

    @Autowired
    UsuarioDAO usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usuarioDao.findOneByUserName(username)
            .map(u -> {
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(u.getROL()));
                return new User(u.getUsername(), u.getPassword(), authorities);
            }).orElseThrow( () -> new UsuarioNoEncontradoException("No se ha encontrado al usuario en la autenticación."));
    }

}
