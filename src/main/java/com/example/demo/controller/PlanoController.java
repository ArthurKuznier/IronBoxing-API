package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.demo.model.Plano;
import com.example.demo.service.PlanoService;
import java.util.List;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @PostMapping
    public ResponseEntity<Plano> criarPlano(@Valid @RequestBody Plano plano) {
        Plano criado = planoService.criarPlano(plano);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plano> atualizarPlano(@PathVariable Integer id, @Valid @RequestBody Plano plano) {
        plano.setId(id);
        Plano atualizado = planoService.atualizarPlano(plano);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Plano>> listarPlanos() {
        return ResponseEntity.ok(planoService.listarPlanos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscarPorId(@PathVariable Integer id) {
        return planoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPlano(@PathVariable Integer id) {
        planoService.deletarPlano(id);
        return ResponseEntity.noContent().build();
    }
}
