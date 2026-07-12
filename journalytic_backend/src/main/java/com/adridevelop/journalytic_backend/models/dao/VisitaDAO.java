package com.adridevelop.journalytic_backend.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adridevelop.journalytic_backend.models.entities.Visita;

@Repository
public interface VisitaDAO extends JpaRepository<Visita, Long>{

}
