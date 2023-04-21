package com.api.devmed.repositories;

import com.api.devmed.model.entities.Consulta;
import com.api.devmed.model.entities.Medico;
import com.api.devmed.model.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteId(Long paciente);
    List<Consulta> findByPacienteAndDataHoraBetween(Paciente paciente, LocalDateTime horaInicio, LocalDateTime horaFim);
    List<Consulta> findByMedicoAndDataHoraBetween(Medico medico,LocalDateTime horaInicio, LocalDateTime horaFim);

    Page<Consulta> findAllByMedicoAtivoTrue(Pageable pageable);
}
