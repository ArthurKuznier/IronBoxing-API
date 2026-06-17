package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Pagamento;
import com.example.demo.repository.PagamentoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Pagamento criarPagamento(Pagamento pagamento) {
        if (pagamento.getDataPagamento() == null) {
            pagamento.setDataPagamento(LocalDateTime.now());
        }
        if (pagamento.getSituacao() == null || pagamento.getSituacao().isBlank()) {
            pagamento.setSituacao("P"); // "P" para Pago
        }
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento atualizarPagamento(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public void deletarPagamento(Integer id) {
        pagamentoRepository.deleteById(id);
    }

    public Optional<Pagamento> buscarPorId(Integer id) {
        return pagamentoRepository.findById(id);
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }

    public List<Pagamento> buscarPorMatriculaId(Integer matriculaId) {
        return pagamentoRepository.findByMatriculaId(matriculaId);
    }

    public List<Pagamento> buscarPorDataPagamento(LocalDateTime dataPagamento) {
        return pagamentoRepository.findByDataPagamento(dataPagamento);
    }

    public List<Pagamento> buscarPorSituacao(String situacao) {
        return pagamentoRepository.findBySituacao(situacao);
    }

    public List<Pagamento> buscarPorValorPago(BigDecimal valorPago) {
        return pagamentoRepository.findByValorPago(valorPago);
    }

    public List<Pagamento> buscarPorFormaPagamento(String formaPagamento) {
        return pagamentoRepository.findByFormaPagamento(formaPagamento);
    }
}
