package com.ironboxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.ironboxing.model.Plano;
import com.ironboxing.service.PlanoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/planos")
@Tag(name = "Planos", description = "Endpoints de gerenciamento de planos da academia")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @PostMapping
    @Operation(summary = "Criar novo plano", description = "Cadastra um novo plano de cobranÃ§a da academia (ex: mensal, anual). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plano criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Plano> criarPlano(@Valid @RequestBody Plano plano) {
        Plano criado = planoService.criarPlano(plano);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar plano existente", description = "Atualiza os dados de um plano cadastrado pelo ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "Plano nÃ£o encontrado")
    })
    public ResponseEntity<Plano> atualizarPlano(@PathVariable Integer id, @Valid @RequestBody Plano plano) {
        plano.setId(id);
        Plano atualizado = planoService.atualizarPlano(plano);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os planos", description = "Retorna a lista de todos os planos cadastrados na academia. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Plano>> listarPlanos() {
        return ResponseEntity.ok(planoService.listarPlanos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar plano por ID", description = "Retorna os detalhes de um plano pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plano retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "Plano nÃ£o encontrado")
    })
    public ResponseEntity<Plano> buscarPorId(@PathVariable Integer id) {
        return planoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um plano", description = "Remove um plano do sistema pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Plano excluÃ­do com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Void> deletarPlano(@PathVariable Integer id) {
        planoService.deletarPlano(id);
        return ResponseEntity.noContent().build();
    }
}

