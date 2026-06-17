package com.ironboxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ironboxing.model.AvaliacaoFisica;
import com.ironboxing.repository.AvaliacaoFisicaRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Service
public class AvaliacaoFisicaService {

    @Autowired
    private AvaliacaoFisicaRepository avaliacaoFisicaRepository;

    private void calcularIMC(AvaliacaoFisica avaliacao) {
        if (avaliacao.getPeso() != null && avaliacao.getAltura() != null && avaliacao.getAltura() > 0) {
            double alturaMetros = avaliacao.getAltura() / 100.0;
            double imcCalculado = avaliacao.getPeso().doubleValue() / (alturaMetros * alturaMetros);
            avaliacao.setImc(BigDecimal.valueOf(imcCalculado).setScale(2, RoundingMode.HALF_UP));
        }
    }

    public AvaliacaoFisica criarAvaliacaoFisica(AvaliacaoFisica avaliacao) {
        if (avaliacao.getDataAvaliacao() == null) {
            avaliacao.setDataAvaliacao(LocalDate.now());
        }
        calcularIMC(avaliacao);
        return avaliacaoFisicaRepository.save(avaliacao);
    }

    public AvaliacaoFisica atualizarAvaliacaoFisica(AvaliacaoFisica avaliacao) {
        calcularIMC(avaliacao);
        return avaliacaoFisicaRepository.save(avaliacao);
    }

    public void deletarAvaliacaoFisica(Integer id) {
        avaliacaoFisicaRepository.deleteById(id);
    }

    public Optional<AvaliacaoFisica> buscarPorId(Integer id) {
        return avaliacaoFisicaRepository.findById(id);
    }

    public List<AvaliacaoFisica> listarAvaliacoesFisicas() {
        return avaliacaoFisicaRepository.findAll();
    }

    public List<AvaliacaoFisica> buscarPorAtletaId(Integer atletaId) {
        return avaliacaoFisicaRepository.findByAtletaId(atletaId);
    }

    public List<AvaliacaoFisica> buscarPorDataAvaliacao(LocalDate dataAvaliacao) {
        return avaliacaoFisicaRepository.findByDataAvaliacao(dataAvaliacao);
    }

    public List<AvaliacaoFisica> buscarPorImc(BigDecimal imc) {
        return avaliacaoFisicaRepository.findByImc(imc);
    }

    public List<AvaliacaoFisica> buscarPorPeso(BigDecimal peso) {
        return avaliacaoFisicaRepository.findByPeso(peso);
    }

    public List<AvaliacaoFisica> buscarPorAltura(Integer altura) {
        return avaliacaoFisicaRepository.findByAltura(altura);
    }
}

