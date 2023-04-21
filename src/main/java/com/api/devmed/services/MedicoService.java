package com.api.devmed.services;

import com.api.devmed.model.dto.MedicoDTO;
import com.api.devmed.model.entities.Medico;
import com.api.devmed.repositories.MedicoRepository;
import com.api.devmed.services.exceptions.DataBaseException;
import com.api.devmed.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public Page<MedicoDTO> listarMedicosAtivos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(MedicoDTO::new);
    }
    public MedicoDTO listarMedicoPorId(Long id) {
        return repository.findById(id).map(MedicoDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado."));
    }
    @Transactional
    public MedicoDTO cadastrarMedico(MedicoDTO dto) {
        Medico entity = new Medico();
        copiarDtoParaEntity(dto, entity);
        repository.save(entity);
        return new MedicoDTO(entity);
    }
    @Transactional
    public MedicoDTO atualizarMedico(Long id, MedicoDTO dto) {
        try{
            Medico entity = repository.getReferenceById(id);
            copiarDtoParaEntity(dto, entity);
            repository.save(entity);
            return new MedicoDTO(entity);
        }
        catch (EntityNotFoundException exception){
            throw new ResourceNotFoundException("Médico não existe.");
        }
    }
    @Transactional
    public void deletarMedico(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException("Não foi possível deletar esse Médico, pois ele não existe.");
        }
        catch (DataIntegrityViolationException exception) {
            throw new DataBaseException("Não foi possível deletar esse Médico, pois viola a integridade do banco de dados.");
        }
    }
    public void copiarDtoParaEntity(MedicoDTO dto, Medico entity){
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setCrm(dto.getCrm());
        entity.setEspecialidade(dto.getEspecialidade());
        entity.setEndereco(dto.getEndereco());
        entity.setAtivo(dto.isAtivo());
    }

}
