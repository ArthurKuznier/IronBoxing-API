package com.example.demo.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "O nome do usuário é obrigatório")
    private String nome;
    @NotBlank(message = "O email do usuário é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;
    @NotBlank(message = "A senha do usuário é obrigatória")
    private String senha;
    @NotBlank(message = "O cpf do usuário é obrigatório")
    private String cpf;
    @NotBlank(message = "O telefone do usuário é obrigatório")
    private String telefone;
    @NotBlank(message = "O endereço do usuário é obrigatório")
    private String endereco;
    @NotBlank(message = "O cep do usuário é obrigatório")
    private String cep;
    @NotBlank(message = "A cidade do usuário é obrigatória")
    private String cidade;
    @NotBlank(message = "O estado do usuário é obrigatório")
    private String estado;
    @NotNull(message = "A data de nascimento do usuário é obrigatória")
    private LocalDate dataNascimento;
    @NotBlank(message = "O gênero do usuário é obrigatório")
    @Column(columnDefinition = "char(1)")
    @org.hibernate.annotations.JdbcTypeCode(java.sql.Types.CHAR)
    private String genero;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "A role do usuário é obrigatória")
    private UserRole.Role role;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String email, String senha, String cpf, String telefone, String endereco,
            String cep,
            String cidade, String estado, LocalDate data_nascimento, String genero, UserRole.Role role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.dataNascimento = data_nascimento;
        this.genero = genero;
        this.role = role;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getData_nascimento() {
        return this.dataNascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.dataNascimento = data_nascimento;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public UserRole.Role getRole() {
        return this.role;
    }

    public void setRole(UserRole.Role role) {
        this.role = role;
    }
}
