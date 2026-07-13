package com.adridevelop.journalytic_backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adridevelop.journalytic_backend.models.entities.Visita;
import com.adridevelop.journalytic_backend.models.service.GeoIpService;
import com.adridevelop.journalytic_backend.models.service.VisitaServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class VisitasController {

    @Autowired
    private VisitaServiceImpl visitaService;

    @Autowired
    private GeoIpService geoIpService;

    @GetMapping("/visitas/pais/{pais}")
    public ResponseEntity<List<Visita>> geVisitasPais(@RequestParam String pais) {
        List<Visita> visitas = this.visitaService.getAll();

        List<Visita> visitasPais = visitas.stream()
        .filter(visita -> pais.equals(this.geoIpService.obtenerCodigoPorPais(visita.getIp())))
        .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(visitasPais);
    }

    @GetMapping("/visitas")
    public ResponseEntity<List<Visita>> getVisitas() {
        return ResponseEntity.status(HttpStatus.OK).body(this.visitaService.getAll());
    }

    @GetMapping("/visita/{id}")
    public ResponseEntity<Visita> getMethodName(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.visitaService.getOneById(id));
    }

    @PostMapping("/visita")
    public ResponseEntity<Visita> save(@RequestBody Visita visita) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.visitaService.save(visita));
    }
    
}
