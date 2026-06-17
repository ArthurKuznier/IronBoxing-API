package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Plano;
import com.example.demo.repository.PlanoRepository;
import java.util.Optional;
import java.util.List;

@Service
public class PlanoService {
    @Autowired
    private PlanoRepository planoRepository;

    public Plano criarPlano(Plano plano) {
        Optional<Plano> nomeExistente = planoRepository.findByNome(plano.getNome());
        if (nomeExistente.isPresent()) {
            throw new RuntimeException("Plano já cadastrado");
        }
        Optional<Plano> valorExistente = planoRepository.findByValor(plano.getValor());
        if (valorExistente.isPresent()) {
            throw new RuntimeException("Plano já cadastrado com esse valor");
        }
        return planoRepository.save(plano);
    }

    public Plano atualizarPlano(Plano plano) {
        Optional<Plano> nomeExistente = planoRepository.findByNome(plano.getNome());
        if (nomeExistente.isPresent() && !nomeExistente.get().getId().equals(plano.getId())) {
            throw new RuntimeException("Plano já cadastrado");
        }
        Optional<Plano> valorExistente = planoRepository.findByValor(plano.getValor());
        if (valorExistente.isPresent() && !valorExistente.get().getId().equals(plano.getId())) {
            throw new RuntimeException("Plano já cadastrado com esse valor");
        }
        return planoRepository.save(plano);
    }

    public void deletarPlano(Integer id) {
        planoRepository.deleteById(id);
    }

    public Optional<Plano> buscarPorId(Integer id) {
        return planoRepository.findById(id);
    }

    public List<Plano> listarPlanos() {
        return planoRepository.findAll();
    }

}
