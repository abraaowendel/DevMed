package com.api.devmed.model.dto;

import com.api.devmed.model.entities.Endereco;
import com.api.devmed.model.entities.Paciente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PacienteDTO {
    private static final long serialVersionUUID = 1L;
    private Long id;
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;
    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;
    @NotBlank(message = "Numéro de telefone é obrigatório.")
    private String telefone;
    @NotNull(message = "Endereço é obrigatório.")
    @Valid
    private Endereco endereco;
    @NotNull(message = "Status de atividade do paciente é obrigatório.")
    private boolean ativo;
    public PacienteDTO() {
    }

    public PacienteDTO(Long id, String nome, String email, String telefone, Endereco endereco, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.ativo = ativo;
    }

    public PacienteDTO(Paciente entity) {
        id = entity.getId();
        nome = entity.getNome();
        email = entity.getEmail();
        telefone = entity.getTelefone();
        endereco = entity.getEndereco();
        ativo = entity.isAtivo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
