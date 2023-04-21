package com.api.devmed.model.dto;

import com.api.devmed.model.entities.Consulta;
import com.api.devmed.model.entities.Medico;
import com.api.devmed.model.entities.Paciente;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ConsultaDTO implements Serializable {
    private static final long serialVersionUUID = 1L;
    private Long id;
    @NotNull(message = "Dados do paciente são obrigatórios.")
    private Paciente paciente;
    @NotNull(message = "Dados do Médico são obrigatórios.")
    private Medico medico;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Future(message = "dataHora/Hora inválidos.")
    private LocalDateTime dataHora;

    public ConsultaDTO() {
    }

    public ConsultaDTO(Long id, Paciente paciente, Medico medico, LocalDateTime dataHora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
    }
    public ConsultaDTO(Consulta entity) {
        id = entity.getId();
        paciente = entity.getPaciente();
        medico = entity.getMedico();
        dataHora = entity.getDataHora();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
