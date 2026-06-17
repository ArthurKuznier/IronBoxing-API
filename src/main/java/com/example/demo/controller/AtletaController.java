package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.demo.model.Atleta;
import com.example.demo.service.AtletaService;
import java.util.List;

@RestController
@RequestMapping("/api/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

    @PostMapping
    public ResponseEntity<Atleta> criarAtleta(@Valid @RequestBody Atleta atleta) {
        Atleta criado = atletaService.criarAtleta(atleta);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atleta> atualizarAtleta(@PathVariable Integer id, @Valid @RequestBody Atleta atleta) {
        atleta.setId(id);
        Atleta atualizado = atletaService.atualizarAtleta(atleta);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Atleta>> listarAtletas() {
        return ResponseEntity.ok(atletaService.listarAtletas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atleta> buscarPorId(@PathVariable Integer id) {
        return atletaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAtleta(@PathVariable Integer id) {
        atletaService.deletarAtleta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Atleta> buscarPorEmail(@PathVariable String email) {
        return atletaService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Atleta> buscarPorCpf(@PathVariable String cpf) {
        return atletaService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<Atleta> buscarPorTelefone(@PathVariable String telefone) {
        return atletaService.buscarPorTelefone(telefone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Atleta>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(atletaService.buscarPorGenero(genero));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Atleta> buscarPorUsuarioId(@PathVariable Integer usuarioId) {
        return atletaService.buscarPorUsuarioId(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
