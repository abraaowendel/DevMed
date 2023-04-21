package com.api.devmed.model.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

@Embeddable
public class Endereco implements Serializable {
        private static final long serialVersionUUID = 1L;
        @NotBlank(message = "Logradouro não pode ser vazio.")
        private String logradouro;
        private String numero;
        private String complemento;
        @NotBlank(message = "Bairro não pode ser vazio.")
        private String bairro;
        @NotBlank(message = "Cidade não pode ser vazio.")
        private String cidade;
        @NotBlank(message = "UF não pode ser vazio.")
        private String uf;
        @NotBlank(message = "CEP não pode ser vazio.")
        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido.")
        private String cep;

        public Endereco() {
        }


        public Endereco(String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) {
            this.logradouro = logradouro;
            this.numero = numero;
            this.complemento = complemento;
            this.bairro = bairro;
            this.cidade = cidade;
            this.uf = uf;
            this.cep = cep;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getComplemento() {
            return complemento;
        }

        public void setComplemento(String complemento) {
            this.complemento = complemento;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }
}
