package com.api.devmed.controllers;

import com.api.devmed.model.dto.PacienteDTO;
import com.api.devmed.services.PacienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<PacienteDTO>> listarPacientes(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarPacientesAtivos(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> listarPacientePorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarPacientePorId(id));
    }
    @PostMapping
    public ResponseEntity<PacienteDTO> cadastrarPaciente(@RequestBody @Valid PacienteDTO dto){
        dto = service.cadastrarPaciente(dto);
        URI uri =ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizarPaciente(@PathVariable Long id, @RequestBody @Valid PacienteDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarPaciente(id, dto));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id){
        service.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
