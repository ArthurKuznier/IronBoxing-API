package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.example.demo.model.Matricula;
import com.example.demo.service.MatriculaService;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<Matricula> criarMatricula(@Valid @RequestBody Matricula matricula) {
        Matricula criada = matriculaService.criarMatricula(matricula);
        return ResponseEntity.ok(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> atualizarMatricula(@PathVariable Integer id,
            @Valid @RequestBody Matricula matricula) {
        matricula.setId(id);
        Matricula atualizada = matriculaService.atualizarMatricula(matricula);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    public ResponseEntity<List<Matricula>> listarMatriculas() {
        return ResponseEntity.ok(matriculaService.listarMatriculas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> buscarPorId(@PathVariable Integer id) {
        return matriculaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMatricula(@PathVariable Integer id) {
        matriculaService.deletarMatricula(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/atleta/{atletaId}")
    public ResponseEntity<List<Matricula>> buscarPorAtletaId(@PathVariable Integer atletaId) {
        return ResponseEntity.ok(matriculaService.buscarPorAtletaId(atletaId));
    }

    @GetMapping("/plano/{planoId}")
    public ResponseEntity<List<Matricula>> buscarPorPlanoId(@PathVariable Integer planoId) {
        return ResponseEntity.ok(matriculaService.buscarPorPlanoId(planoId));
    }

    @GetMapping("/situacao/{situacaoMat}")
    public ResponseEntity<List<Matricula>> buscarPorSituacaoMat(@PathVariable String situacaoMat) {
        return ResponseEntity.ok(matriculaService.buscarPorSituacaoMat(situacaoMat));
    }

    @GetMapping("/data/{dataMatricula}")
    public ResponseEntity<List<Matricula>> buscarPorDataMatricula(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMatricula) {
        return ResponseEntity.ok(matriculaService.buscarPorDataMatricula(dataMatricula));
    }

    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<Matricula>> buscarPorTurmaId(@PathVariable Integer turmaId) {
        return ResponseEntity.ok(matriculaService.buscarPorTurmaId(turmaId));
    }
}
