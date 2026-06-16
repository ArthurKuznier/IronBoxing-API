package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Matricula;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    List<Matricula> findByAtletaId(Integer atletaId);

    List<Matricula> findByPlanoId(Integer planoId);

    List<Matricula> findBySituacaoMat(String situacaoMat);

    List<Matricula> findByDataMatricula(LocalDate dataMatricula);
    
    List<Matricula> findByTurmaId(Integer turmaId);
}
