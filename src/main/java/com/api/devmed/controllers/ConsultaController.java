package com.api.devmed.controllers;

import com.api.devmed.model.dto.ConsultaDTO;
import com.api.devmed.repositories.ConsultaRepository;
import com.api.devmed.repositories.MedicoRepository;
import com.api.devmed.repositories.PacienteRepository;
import com.api.devmed.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ConsultaDTO>> listarTodasConsultas(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodasConsultas(pageable));
    }
    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<ConsultaDTO>> listarConsultasIdPaciente(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarConsultaIdPaciente(id));
    }
    @PostMapping
    public ResponseEntity<ConsultaDTO> agendarConsultar(@RequestBody @Valid ConsultaDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.agendarConsulta(dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarConsulta(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
