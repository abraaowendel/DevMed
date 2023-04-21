package com.api.devmed.model.dto;

import com.api.devmed.model.entities.Endereco;
import com.api.devmed.model.entities.Medico;
import com.api.devmed.model.enums.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class MedicoDTO {
    private static final long serialVersionUUID = 1L;
    private Long id;
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;
    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;
    @NotBlank(message = "Numéro de Celular é obrigatório.")
    private String telefone;
    @NotBlank(message = "CRM é obrigatório.")
    @Pattern(regexp = "^CRM\\/[A-Z]{2}\\s\\d{6}$", message = "CRM inválido.")
    private String crm;
    @NotNull(message = "Especialidade é obrigatória.")
    private Especialidade especialidade;
    @NotNull(message = "Endereço é obrigatório.")
    @Valid
    private Endereco endereco;
    private boolean ativo;

    public MedicoDTO() {
    }

    public MedicoDTO(Long id, String nome, String email, String telefone, String crm, Especialidade especialidade, Endereco endereco, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.crm = crm;
        this.especialidade = especialidade;
        this.endereco = endereco;
        this.ativo = ativo;
    }

    public MedicoDTO(Medico entity) {
        id = entity.getId();
        nome = entity.getNome();
        email = entity.getEmail();
        telefone = entity.getTelefone();
        crm = entity.getCrm();
        especialidade = entity.getEspecialidade();
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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicoDTO medicoDTO = (MedicoDTO) o;
        return Objects.equals(id, medicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
