package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AtletaRepository;
import com.example.demo.model.Atleta;
import java.util.List;
import java.util.Optional;

@Service
public class AtletaService {
    @Autowired
    private AtletaRepository atletaRepository;

    public Atleta criarAtleta(Atleta atleta) {
        Optional<Atleta> atletaCpfExistente = atletaRepository.findByUsuarioCpf(atleta.getUsuario().getCpf());
        if (atletaCpfExistente.isPresent()) {
            throw new RuntimeException("Atleta já cadastrado, faça o login");
        }
        Optional<Atleta> atletaEmailExistente = atletaRepository.findByUsuarioEmail(atleta.getUsuario().getEmail());
        if (atletaEmailExistente.isPresent()) {
            throw new RuntimeException("Atleta já cadastrado, faça o login");
        }
        Optional<Atleta> atletaTelefoneExistente = atletaRepository
                .findByUsuarioTelefone(atleta.getUsuario().getTelefone());
        if (atletaTelefoneExistente.isPresent()) {
            throw new RuntimeException("Atleta já cadastrado, faça o login");
        }
        return atletaRepository.save(atleta);
    }

    public Atleta atualizarAtleta(Atleta atleta) {
        return atletaRepository.save(atleta);
    }

    public void deletarAtleta(Integer id) {
        atletaRepository.deleteById(id);
    }

    public Optional<Atleta> buscarPorId(Integer id) {
        return atletaRepository.findById(id);
    }

    public List<Atleta> listarAtletas() {
        return atletaRepository.findAll();
    }

}
