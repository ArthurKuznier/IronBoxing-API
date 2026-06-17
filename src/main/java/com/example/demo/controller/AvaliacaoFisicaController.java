package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.example.demo.model.AvaliacaoFisica;
import com.example.demo.service.AvaliacaoFisicaService;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoFisicaController {

    @Autowired
    private AvaliacaoFisicaService avaliacaoFisicaService;

    @PostMapping
    public ResponseEntity<AvaliacaoFisica> criarAvaliacaoFisica(@Valid @RequestBody AvaliacaoFisica avaliacao) {
        AvaliacaoFisica criada = avaliacaoFisicaService.criarAvaliacaoFisica(avaliacao);
        return ResponseEntity.ok(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoFisica> atualizarAvaliacaoFisica(@PathVariable Integer id,
            @Valid @RequestBody AvaliacaoFisica avaliacao) {
        avaliacao.setId(id);
        AvaliacaoFisica atualizada = avaliacaoFisicaService.atualizarAvaliacaoFisica(avaliacao);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoFisica>> listarAvaliacoesFisicas() {
        return ResponseEntity.ok(avaliacaoFisicaService.listarAvaliacoesFisicas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoFisica> buscarPorId(@PathVariable Integer id) {
        return avaliacaoFisicaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacaoFisica(@PathVariable Integer id) {
        avaliacaoFisicaService.deletarAvaliacaoFisica(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/atleta/{atletaId}")
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorAtletaId(@PathVariable Integer atletaId) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorAtletaId(atletaId));
    }

    @GetMapping("/data/{dataAvaliacao}")
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorDataAvaliacao(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAvaliacao) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorDataAvaliacao(dataAvaliacao));
    }

    @GetMapping("/imc/{imc}")
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorImc(@PathVariable BigDecimal imc) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorImc(imc));
    }

    @GetMapping("/peso/{peso}")
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorPeso(@PathVariable BigDecimal peso) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorPeso(peso));
    }

    @GetMapping("/altura/{altura}")
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorAltura(@PathVariable Integer altura) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorAltura(altura));
    }
}
