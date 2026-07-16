package com.adridevelop.journalytic_backend.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adridevelop.journalytic_backend.models.entities.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findOneByUserName(String username);

}
