package com.ironboxing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ironboxing.model.Turma;
import java.util.Optional;
import java.util.List;
import java.time.LocalTime;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    Optional<Turma> findByDescricao(String descricao);

    List<Turma> findByHorario(LocalTime horario);

    List<Turma> findByTreinadorId(int treinadorId);
    

}

