package com.api.devmed.services;

import com.api.devmed.model.dto.ConsultaDTO;
import com.api.devmed.model.dto.PacienteDTO;
import com.api.devmed.model.entities.Consulta;
import com.api.devmed.model.entities.Paciente;
import com.api.devmed.repositories.PacienteRepository;
import com.api.devmed.services.exceptions.DataBaseException;
import com.api.devmed.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.DayOfWeek.SUNDAY;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Page<PacienteDTO> listarPacientesAtivos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(PacienteDTO::new);
    }

    @Transactional
    public PacienteDTO listarPacientePorId(Long id) {
        return repository.findById(id).map(PacienteDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado."));
    }

    @Transactional
    public PacienteDTO cadastrarPaciente(PacienteDTO dto) {
        Paciente entity = new Paciente();
        copiarDtoParaEntity(dto, entity);
        repository.save(entity);
        return new PacienteDTO(entity);
    }

    @Transactional
    public PacienteDTO atualizarPaciente(Long id, PacienteDTO dto) {
        try {
            Paciente entity = repository.getReferenceById(id);
            copiarDtoParaEntity(dto, entity);
            repository.save(entity);
            return new PacienteDTO(entity);
        }
        catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException("Paciente não encontrado.");
        }
    }

    public void deletarPaciente(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException("Não foi possível deletar esse paciente, pois ele não existe.");
        }
        catch (DataIntegrityViolationException exception) {
            throw new DataBaseException("Não foi possível deletar esse paciente, pois viola a integridade do banco de dados.");
        }
    }

    public void copiarDtoParaEntity(PacienteDTO dto, Paciente entity){
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setEndereco(dto.getEndereco());
        entity.setAtivo(dto.isAtivo());
    }



}
