package com.ironboxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ironboxing.model.Matricula;
import com.ironboxing.repository.MatriculaRepository;
import java.util.Optional;
import java.util.List;
import java.time.LocalDate;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    public Matricula criarMatricula(Matricula matricula) {
        if (matricula.getDataMatricula() == null) {
            matricula.setDataMatricula(LocalDate.now());
        }
        if (matricula.getSituacaoMat() == null) {
            matricula.setSituacaoMat("A");
        }
        boolean jaAtivo = matriculaRepository.findByAtletaId(matricula.getAtleta().getId()).stream()
                .anyMatch(m -> m.getTurma().getId().equals(matricula.getTurma().getId())
                        && "A".equals(m.getSituacaoMat()));
        if (jaAtivo) {
            throw new RuntimeException("Atleta jÃ¡ possui matrÃ­cula ativa nesta turma");
        }
        return matriculaRepository.save(matricula);
    }

    public Matricula atualizarMatricula(Matricula matricula) {
        boolean jaAtivo = matriculaRepository.findByAtletaId(matricula.getAtleta().getId()).stream()
                .anyMatch(m -> m.getTurma().getId().equals(matricula.getTurma().getId())
                        && "A".equals(m.getSituacaoMat()) && !m.getId().equals(matricula.getId()));
        if (jaAtivo) {
            throw new RuntimeException("Atleta jÃ¡ possui matrÃ­cula ativa nesta turma");
        }
        return matriculaRepository.save(matricula);
    }

    public void deletarMatricula(Integer id) {
        matriculaRepository.deleteById(id);
    }

    public Optional<Matricula> buscarPorId(Integer id) {
        return matriculaRepository.findById(id);
    }

    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }

    public List<Matricula> buscarPorAtletaId(Integer atletaId) {
        return matriculaRepository.findByAtletaId(atletaId);
    }

    public List<Matricula> buscarPorPlanoId(Integer planoId) {
        return matriculaRepository.findByPlanoId(planoId);
    }

    public List<Matricula> buscarPorSituacaoMat(String situacaoMat) {
        return matriculaRepository.findBySituacaoMat(situacaoMat);
    }

    public List<Matricula> buscarPorDataMatricula(LocalDate dataMatricula) {
        return matriculaRepository.findByDataMatricula(dataMatricula);
    }

    public List<Matricula> buscarPorTurmaId(Integer turmaId) {
        return matriculaRepository.findByTurmaId(turmaId);
    }
}

