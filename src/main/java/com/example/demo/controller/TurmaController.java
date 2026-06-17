package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.example.demo.model.Turma;
import com.example.demo.service.TurmaService;
import java.util.List;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    public ResponseEntity<Turma> criarTurma(@Valid @RequestBody Turma turma) {
        Turma criada = turmaService.criarTurma(turma);
        return ResponseEntity.ok(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> atualizarTurma(@PathVariable Integer id, @Valid @RequestBody Turma turma) {
        turma.setId(id);
        Turma atualizada = turmaService.atualizarTurma(turma);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    public ResponseEntity<List<Turma>> listarTurmas() {
        return ResponseEntity.ok(turmaService.listarTurmas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarPorId(@PathVariable Integer id) {
        return turmaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable Integer id) {
        turmaService.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<Turma> buscarPorDescricao(@PathVariable String descricao) {
        return turmaService.buscarPorDescricao(descricao).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/horario/{horario}")
    public ResponseEntity<List<Turma>> buscarPorHorario(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horario) {
        return ResponseEntity.ok(turmaService.buscarPorHorario(horario));
    }

    @GetMapping("/treinador/{treinadorId}")
    public ResponseEntity<List<Turma>> buscarPorTreinadorId(@PathVariable int treinadorId) {
        return ResponseEntity.ok(turmaService.buscarPorTreinadorId(treinadorId));
    }
}
