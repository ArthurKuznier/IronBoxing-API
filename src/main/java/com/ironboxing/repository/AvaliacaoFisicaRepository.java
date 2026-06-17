package com.ironboxing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ironboxing.model.AvaliacaoFisica;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Integer> {

    List<AvaliacaoFisica> findByAtletaId(Integer atletaId);

    List<AvaliacaoFisica> findByDataAvaliacao(LocalDate dataAvaliacao);

    List<AvaliacaoFisica> findByImc(BigDecimal imc);

    List<AvaliacaoFisica> findByPeso(BigDecimal peso);

    List<AvaliacaoFisica> findByAltura(Integer altura);
}

