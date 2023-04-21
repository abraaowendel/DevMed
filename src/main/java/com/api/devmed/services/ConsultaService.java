package com.api.devmed.services;

import com.api.devmed.model.dto.ConsultaDTO;
import com.api.devmed.model.entities.Consulta;
import com.api.devmed.model.entities.Medico;
import com.api.devmed.model.entities.Paciente;
import com.api.devmed.repositories.ConsultaRepository;
import com.api.devmed.repositories.MedicoRepository;
import com.api.devmed.repositories.PacienteRepository;
import com.api.devmed.services.exceptions.DataBaseException;
import com.api.devmed.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.DayOfWeek.SUNDAY;

@Service
public class ConsultaService {
    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public Page<ConsultaDTO> buscarTodasConsultas(Pageable pageable) {
        return repository.findAllByMedicoAtivoTrue(pageable).map(ConsultaDTO::new);
    }

    public List<ConsultaDTO> buscarConsultaIdPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream().map(ConsultaDTO::new).toList();
    }

    @Transactional
    public ConsultaDTO agendarConsulta(ConsultaDTO dto) {
        Consulta entity = new Consulta();
        validarConsulta(dto);
        copiarDtoParaEntity(dto, entity);
        repository.save(entity);
        return new ConsultaDTO(entity);
    }
    public void delete(Long id) {
        try{
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException("Não foi possível cancelar esse consulta, pois ela não existe.");
        }
        catch (DataIntegrityViolationException exception){
            throw new DataBaseException("Não foi possível cancelar essa consulta, pois viola a integridade do banco de dados.");
        }
    }

    public void copiarDtoParaEntity(ConsultaDTO dto, Consulta entity){
        entity.setPaciente(dto.getPaciente());
        entity.setMedico(dto.getMedico());
        entity.setDataHora(dto.getDataHora());
    }

    public void validarConsulta(ConsultaDTO dto) {
        verificarAtividadeMedicoUsuario(dto);
        verificarAgendamentoComAntecedencia(dto);
        verificarDataEHoraDeFuncionamento(dto);
        verificarConsultaExistente(dto);
        verificarSeMedicoPossuiConsultaNesseHorario(dto);
    }

    private void verificarAtividadeMedicoUsuario(ConsultaDTO dto) {

        Optional<Medico> isMedico = medicoRepository.findByIdAndAtivoTrue(dto.getMedico().getId());
        Optional<Paciente> isPaciente = pacienteRepository.findByIdAndAtivoTrue(dto.getPaciente().getId());

        if (isMedico.isEmpty()){
            throw new IllegalArgumentException("Médico está inativo no sistema.");
        }
        if(isPaciente.isEmpty()){
            throw new IllegalArgumentException("Paciente está inativo no sistema.");
        }
    }
    private void verificarDataEHoraDeFuncionamento(ConsultaDTO dto){
        DayOfWeek dia = dto.getDataHora().getDayOfWeek();
        int hora = dto.getDataHora().getHour();
        if(hora < 7 || hora > 19){
            throw new IllegalArgumentException("Clinica não funciona nesse horário.");
        }
        if (dia == SUNDAY) {
            throw new IllegalArgumentException("Clinica não funciona aos Domingos.");
        }
    }
    private void verificarAgendamentoComAntecedencia(ConsultaDTO dto){
        if (dto.getDataHora().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalArgumentException("A consulta deve ser agendada com antecedência mínima de 30 minutos.");
        }
    }
    private void verificarConsultaExistente(ConsultaDTO dto) {
        LocalDateTime horaInicio = dto.getDataHora().withHour(7).withMinute(0).withSecond(0);
        LocalDateTime horaFim = dto.getDataHora().withHour(23).withMinute(59).withSecond(59);

        List<Consulta> consultas = repository.findByPacienteAndDataHoraBetween(dto.getPaciente(), horaInicio, horaFim);

        if(!consultas.isEmpty()){
            throw new IllegalArgumentException("Paciente já tem uma consulta nesse dia.");
        }
    }

    private void verificarSeMedicoPossuiConsultaNesseHorario(ConsultaDTO dto) {
        LocalDateTime horaInicio = dto.getDataHora();
        LocalDateTime horaFim = dto.getDataHora().plusMinutes(60);

        List<Consulta> consulta = repository.findByMedicoAndDataHoraBetween(dto.getMedico(), horaInicio, horaFim);

        if(!consulta.isEmpty()){
            throw new IllegalArgumentException("Esse médico já tem uma consulta nesse horário.");
        }
    }

}
