package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Table(name = "turma")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "A descrição da turma é obrigatória")
    private String descricao;
    @NotNull(message = "O horário da turma é obrigatório")
    private LocalTime horario;
    @NotNull(message = "O treinador da turma é obrigatório")
    @ManyToOne
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;

    public Turma() {
    }

    public Turma(Integer id, String descricao, LocalTime horario, Treinador treinador) {
        this.id = id;
        this.descricao = descricao;
        this.horario = horario;
        this.treinador = treinador;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalTime getHorario() {
        return this.horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Treinador getTreinador() {
        return this.treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }
}
