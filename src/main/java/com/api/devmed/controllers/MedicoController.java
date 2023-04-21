package com.api.devmed.controllers;

import com.api.devmed.model.dto.MedicoDTO;
import com.api.devmed.services.MedicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<MedicoDTO>> listarMedicos(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarMedicosAtivos(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> listarMedicoPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarMedicoPorId(id));
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedicoDTO> cadastrarMedico(@RequestBody @Valid MedicoDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarMedico(dto));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedicoDTO> atualizarMedico(@PathVariable Long id, @RequestBody @Valid MedicoDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarMedico(id, dto));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarMedico(@PathVariable Long id){
        service.deletarMedico(id);
        return ResponseEntity.noContent().build();
    }
}
