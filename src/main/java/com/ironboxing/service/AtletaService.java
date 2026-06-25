package com.ironboxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ironboxing.repository.AtletaRepository;
import com.ironboxing.model.Atleta;
import com.ironboxing.exception.BusinessException;
import java.util.List;
import java.util.Optional;

@Service
public class AtletaService {
    @Autowired
    private AtletaRepository atletaRepository;

    public Atleta criarAtleta(Atleta atleta) {
        Optional<Atleta> atletaCpfExistente = atletaRepository.findByUsuarioCpf(atleta.getUsuario().getCpf());
        if (atletaCpfExistente.isPresent()) {
            throw new BusinessException("Atleta já cadastrado, faça o login");
        }
        Optional<Atleta> atletaEmailExistente = atletaRepository.findByUsuarioEmail(atleta.getUsuario().getEmail());
        if (atletaEmailExistente.isPresent()) {
            throw new BusinessException("Atleta já cadastrado, faça o login");
        }
        Optional<Atleta> atletaTelefoneExistente = atletaRepository
                .findByUsuarioTelefone(atleta.getUsuario().getTelefone());
        if (atletaTelefoneExistente.isPresent()) {
            throw new BusinessException("Atleta já cadastrado, faça o login");
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

    public Optional<Atleta> buscarPorEmail(String email) {
        return atletaRepository.findByUsuarioEmail(email);
    }

    public Optional<Atleta> buscarPorCpf(String cpf) {
        return atletaRepository.findByUsuarioCpf(cpf);
    }

    public Optional<Atleta> buscarPorTelefone(String telefone) {
        return atletaRepository.findByUsuarioTelefone(telefone);
    }

    public List<Atleta> buscarPorGenero(String genero) {
        return atletaRepository.findByUsuarioGenero(genero);
    }

    public Optional<Atleta> buscarPorUsuarioId(Integer usuarioId) {
        return atletaRepository.findByUsuarioId(usuarioId);
    }
}

