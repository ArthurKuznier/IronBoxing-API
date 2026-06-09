package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "avaliacao_fisica")
public class AvaliacaoFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "O atleta é obrigatório")
    @ManyToOne
    @JoinColumn(name = "atleta_id")
    private Atleta atleta;
    @NotNull(message = "A data da avaliação é obrigatória")
    private LocalDate dataAvaliacao;
    @NotNull(message = "O peso é obrigatório")
    private BigDecimal peso;
    @NotNull(message = "A altura é obrigatória")
    private Integer altura;
    @NotNull(message = "O imc é obrigatório")
    private BigDecimal imc;

    public AvaliacaoFisica() {
    }

    public AvaliacaoFisica(Integer id, Atleta atleta, LocalDate dataAvaliacao, BigDecimal peso, Integer altura,
            BigDecimal imc) {
        this.id = id;
        this.atleta = atleta;
        this.dataAvaliacao = dataAvaliacao;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
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

    public LocalDate getDataAvaliacao() {
        return this.dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public BigDecimal getPeso() {
        return this.peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Integer getAltura() {
        return this.altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public BigDecimal getImc() {
        return this.imc;
    }

    public void setImc(BigDecimal imc) {
        this.imc = imc;
    }
}
