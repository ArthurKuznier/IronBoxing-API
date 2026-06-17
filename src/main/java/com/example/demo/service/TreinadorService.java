package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Treinador;
import com.example.demo.repository.TreinadorRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TreinadorService {

    @Autowired
    private TreinadorRepository treinadorRepository;

    public Treinador criarTreinador(Treinador treinador) {
        Optional<Treinador> treinadorCpfExistente = treinadorRepository
                .findByUsuarioCpf(treinador.getUsuario().getCpf());
        if (treinadorCpfExistente.isPresent()) {
            throw new RuntimeException("Treinador já cadastrado, faça o login");
        }

        Optional<Treinador> treinadorEmailExistente = treinadorRepository
                .findByUsuarioEmail(treinador.getUsuario().getEmail());
        if (treinadorEmailExistente.isPresent()) {
            throw new RuntimeException("Treinador já cadastrado, faça o login");
        }

        Optional<Treinador> treinadorTelefoneExistente = treinadorRepository
                .findByUsuarioTelefone(treinador.getUsuario().getTelefone());
        if (treinadorTelefoneExistente.isPresent()) {
            throw new RuntimeException("Treinador já cadastrado, faça o login");
        }
        Optional<Treinador> treinadorCrefExistente = treinadorRepository.findByCref(treinador.getCref());
        if (treinadorCrefExistente.isPresent()) {
            throw new RuntimeException("Treinador já cadastrado, faça o login");
        }
        return treinadorRepository.save(treinador);
    }

    public Treinador atualizarTreinador(Treinador treinador) {
        Optional<Treinador> treinadorCpfExistente = treinadorRepository
                .findByUsuarioCpf(treinador.getUsuario().getCpf());
        if (treinadorCpfExistente.isPresent() && !treinadorCpfExistente.get().getId().equals(treinador.getId())) {
            throw new RuntimeException("Dados cadastrais já existentes");
        }

        Optional<Treinador> treinadorEmailExistente = treinadorRepository
                .findByUsuarioEmail(treinador.getUsuario().getEmail());
        if (treinadorEmailExistente.isPresent() && !treinadorEmailExistente.get().getId().equals(treinador.getId())) {
            throw new RuntimeException("Dados cadastrais já existentes");
        }

        Optional<Treinador> treinadorTelefoneExistente = treinadorRepository
                .findByUsuarioTelefone(treinador.getUsuario().getTelefone());
        if (treinadorTelefoneExistente.isPresent() && !treinadorTelefoneExistente.get().getId().equals(treinador.getId())) {
            throw new RuntimeException("Dados cadastrais já existentes");
        }
        Optional<Treinador> treinadorCrefExistente = treinadorRepository.findByCref(treinador.getCref());
        if (treinadorCrefExistente.isPresent() && !treinadorCrefExistente.get().getId().equals(treinador.getId())) {
            throw new RuntimeException("CREF já cadastrado");
        }
        return treinadorRepository.save(treinador);
    }

    public void deletarTreinador(Integer id) {
        treinadorRepository.deleteById(id);
    }

    public Optional<Treinador> buscarPorId(Integer id) {
        return treinadorRepository.findById(id);
    }

    public Optional<Treinador> buscarPorCpf(String cpf) {
        return treinadorRepository.findByUsuarioCpf(cpf);
    }

    public Optional<Treinador> buscarPorEmail(String email) {
        return treinadorRepository.findByUsuarioEmail(email);
    }

    public Optional<Treinador> buscarPorTelefone(String telefone) {
        return treinadorRepository.findByUsuarioTelefone(telefone);
    }

    public Optional<Treinador> buscarPorCref(String cref) {
        return treinadorRepository.findByCref(cref);
    }

    public List<Treinador> buscarPorGenero(String genero) {
        return treinadorRepository.findByUsuarioGenero(genero);
    }

    public List<Treinador> buscarPorEspecialidade(String especialidade) {
        return treinadorRepository.findByEspecialidade(especialidade);
    }

    public Optional<Treinador> buscarPorUsuarioId(Integer usuarioId) {
        return treinadorRepository.findByUsuarioId(usuarioId);
    }

    public List<Treinador> listarTreinadores() {
        return treinadorRepository.findAll();
    }

}
