package com.ironboxing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.sql.Types;

@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "A matrícula é obrigatória")
    @ManyToOne(optional = false)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @NotNull(message = "A data do pagamento é obrigatória")
    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @NotNull(message = "O valor pago é obrigatório")
    @Positive(message = "O valor pago deve ser maior que zero")
    @Column(name = "valor_pago", precision = 12, scale = 2)
    private BigDecimal valorPago;

    @NotBlank(message = "A forma de pagamento é obrigatória")
    @Column(name = "forma_pagamento", length = 20)
    private String formaPagamento;

    @NotBlank(message = "A situação é obrigatória")
    @Column(columnDefinition = "char(1)")
    @org.hibernate.annotations.JdbcTypeCode(Types.CHAR)
    private String situacao;

    public Pagamento() {
    }

    public Pagamento(Integer id, Matricula matricula, LocalDateTime dataPagamento, BigDecimal valorPago,
            String formaPagamento, String situacao) {
        this.id = id;
        this.matricula = matricula;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.formaPagamento = formaPagamento;
        this.situacao = situacao;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Matricula getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public LocalDateTime getDataPagamento() {
        return this.dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorPago() {
        return this.valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getFormaPagamento() {
        return this.formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getSituacao() {
        return this.situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}

