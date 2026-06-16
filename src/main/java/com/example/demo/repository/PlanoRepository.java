package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Plano;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Integer> {

    Optional<Plano> findByNome(String nome);

    Optional<Plano> findByValor(BigDecimal valor);
}
