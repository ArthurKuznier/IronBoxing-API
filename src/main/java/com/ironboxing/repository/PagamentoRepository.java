package com.ironboxing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ironboxing.model.Pagamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    List<Pagamento> findByMatriculaId(Integer matriculaId);

    List<Pagamento> findByDataPagamento(LocalDateTime dataPagamento);

    List<Pagamento> findBySituacao(String situacao);

    List<Pagamento> findByValorPago(BigDecimal valorPago);

    List<Pagamento> findByFormaPagamento(String formaPagamento);
}

