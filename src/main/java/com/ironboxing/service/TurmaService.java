package com.ironboxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ironboxing.model.Turma;
import com.ironboxing.repository.TurmaRepository;
import java.util.Optional;
import java.util.List;
import java.time.LocalTime;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    public Turma criarTurma(Turma turma) {
        Optional<Turma> turmaExistente = turmaRepository.findByDescricao(turma.getDescricao());
        if (turmaExistente.isPresent()) {
            throw new RuntimeException("Turma jÃ¡ cadastrada com essa descriÃ§Ã£o");
        }
        return turmaRepository.save(turma);
    }

    public Turma atualizarTurma(Turma turma) {
        Optional<Turma> turmaExistente = turmaRepository.findByDescricao(turma.getDescricao());
        if (turmaExistente.isPresent() && !turmaExistente.get().getId().equals(turma.getId())) {
            throw new RuntimeException("Turma jÃ¡ cadastrada com essa descriÃ§Ã£o");
        }
        return turmaRepository.save(turma);
    }

    public void deletarTurma(Integer id) {
        turmaRepository.deleteById(id);
    }

    public Optional<Turma> buscarPorId(Integer id) {
        return turmaRepository.findById(id);
    }

    public Optional<Turma> buscarPorDescricao(String descricao) {
        return turmaRepository.findByDescricao(descricao);
    }

    public List<Turma> buscarPorHorario(LocalTime horario) {
        return turmaRepository.findByHorario(horario);
    }

    public List<Turma> buscarPorTreinadorId(int treinadorId) {
        return turmaRepository.findByTreinadorId(treinadorId);
    }

    public List<Turma> listarTurmas() {
        return turmaRepository.findAll();
    }
}

