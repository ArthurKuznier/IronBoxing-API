package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.demo.model.Treinador;
import com.example.demo.service.TreinadorService;
import java.util.List;

@RestController
@RequestMapping("/api/treinadores")
public class TreinadorController {

    @Autowired
    private TreinadorService treinadorService;

    @PostMapping
    public ResponseEntity<Treinador> criarTreinador(@Valid @RequestBody Treinador treinador) {
        Treinador criado = treinadorService.criarTreinador(treinador);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treinador> atualizarTreinador(@PathVariable Integer id,
            @Valid @RequestBody Treinador treinador) {
        treinador.setId(id);
        Treinador atualizado = treinadorService.atualizarTreinador(treinador);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Treinador>> listarTreinadores() {
        return ResponseEntity.ok(treinadorService.listarTreinadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treinador> buscarPorId(@PathVariable Integer id) {
        return treinadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        treinadorService.deletarTreinador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cref/{cref}")
    public ResponseEntity<Treinador> buscarPorCref(@PathVariable String cref) {
        return treinadorService.buscarPorCref(cref).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Treinador> buscarPorEmail(@PathVariable String email) {
        return treinadorService.buscarPorEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Treinador> buscarPorCpf(@PathVariable String cpf) {
        return treinadorService.buscarPorCpf(cpf).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<Treinador> buscarPorTelefone(@PathVariable String telefone) {
        return treinadorService.buscarPorTelefone(telefone).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Treinador>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(treinadorService.buscarPorGenero(genero));
    }

    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<Treinador>> buscarPorEspecialidade(@PathVariable String especialidade) {
        return ResponseEntity.ok(treinadorService.buscarPorEspecialidade(especialidade));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Treinador> buscarPorUsuarioId(@PathVariable Integer usuarioId) {
        return treinadorService.buscarPorUsuarioId(usuarioId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
