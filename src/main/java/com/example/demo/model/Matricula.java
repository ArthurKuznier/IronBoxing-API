package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.sql.Types;
import jakarta.persistence.Column;

@Entity
@Table(name = "matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "O atleta é obrigatório")
    @ManyToOne
    @JoinColumn(name = "atleta_id")
    private Atleta atleta;
    @NotNull(message = "A turma é obrigatória")
    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;
    @NotNull(message = "O plano é obrigatório")
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;
    @NotNull(message = "A data de matrícula é obrigatória")
    private LocalDate dataMatricula;
    @NotBlank(message = "A situação da matrícula é obrigatória")
    @Column(columnDefinition = "char(1)")
    @org.hibernate.annotations.JdbcTypeCode(Types.CHAR)
    private String situacaoMat;

    public Matricula() {
    }

    public Matricula(Integer id, Atleta atleta, Turma turma, Plano plano, LocalDate dataMatricula,
            String situacaoMat) {
        this.id = id;
        this.atleta = atleta;
        this.turma = turma;
        this.plano = plano;
        this.dataMatricula = dataMatricula;
        this.situacaoMat = situacaoMat;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Atleta getAtleta() {
        return this.atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }

    public Turma getTurma() {
        return this.turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Plano getPlano() {
        return this.plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public LocalDate getDataMatricula() {
        return this.dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getSituacaoMat() {
        return this.situacaoMat;
    }

    public void setSituacaoMat(String situacaoMat) {
        this.situacaoMat = situacaoMat;
    }
}
