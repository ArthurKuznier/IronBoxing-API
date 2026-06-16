package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Turma;
import java.util.Optional;
import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    Optional<Turma> findByDescricao(String descricao);

    List<Turma> findByHorario(String horario);

    List<Turma> findByTreinadorId(int treinadorId);

}
