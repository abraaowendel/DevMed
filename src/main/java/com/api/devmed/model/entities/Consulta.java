package com.api.devmed.model.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_CONSULTA")
public class Consulta implements Serializable {
    private static final long serialVersionUUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @Valid
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "medico_id")
    @Valid
    private Medico medico;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Future(message = "Data/Hora inválidos.")
    private LocalDateTime dataHora;

    public Consulta() {
    }

    public Consulta(Long id, Paciente paciente, Medico medico, LocalDateTime dataHora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
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

    public void setDataHora(LocalDateTime data) {
        this.dataHora = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(id, consulta.id) && Objects.equals(paciente, consulta.paciente) && Objects.equals(medico, consulta.medico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paciente, medico);
    }

    @Override
    public String toString() {
        return id + " | " + dataHora.getDayOfMonth() + " | " + paciente.getNome() + " | " + medico.getNome();
    }
}
