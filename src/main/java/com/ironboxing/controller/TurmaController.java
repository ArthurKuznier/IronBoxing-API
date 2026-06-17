package com.ironboxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.ironboxing.model.Turma;
import com.ironboxing.service.TurmaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/turmas")
@Tag(name = "Turmas", description = "Endpoints de gerenciamento e consulta de turmas e horÃ¡rios")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    @Operation(summary = "Criar nova turma", description = "Cadastra uma nova turma e seu horÃ¡rio correspondente, vinculando a um treinador. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Turma criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Turma> criarTurma(@Valid @RequestBody Turma turma) {
        Turma criada = turmaService.criarTurma(turma);
        return ResponseEntity.ok(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de uma turma", description = "Atualiza os dados de uma turma existente pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Turma atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "Turma nÃ£o encontrada")
    })
    public ResponseEntity<Turma> atualizarTurma(@PathVariable Integer id, @Valid @RequestBody Turma turma) {
        turma.setId(id);
        Turma atualizada = turmaService.atualizarTurma(turma);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    @Operation(summary = "Listar todas as turmas", description = "Retorna a lista de todas as turmas cadastradas na academia. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Turma>> listarTurmas() {
        return ResponseEntity.ok(turmaService.listarTurmas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar turma por ID", description = "Retorna os detalhes de uma turma especÃ­fica pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Turma retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "Turma nÃ£o encontrada")
    })
    public ResponseEntity<Turma> buscarPorId(@PathVariable Integer id) {
        return turmaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma turma", description = "Remove uma turma do sistema pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Turma excluÃ­da com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Void> deletarTurma(@PathVariable Integer id) {
        turmaService.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/descricao/{descricao}")
    @Operation(summary = "Buscar turma por descriÃ§Ã£o", description = "Retorna os detalhes de uma turma filtrada pela sua descriÃ§Ã£o. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Turma localizada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "Turma nÃ£o encontrada")
    })
    public ResponseEntity<Turma> buscarPorDescricao(@PathVariable String descricao) {
        return turmaService.buscarPorDescricao(descricao).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/horario/{horario}")
    @Operation(summary = "Buscar turmas por horÃ¡rio", description = "Retorna uma lista de turmas baseada em um horÃ¡rio de inÃ­cio especÃ­fico (formato HH:mm:ss ou HH:mm). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Turma>> buscarPorHorario(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horario) {
        return ResponseEntity.ok(turmaService.buscarPorHorario(horario));
    }

    @GetMapping("/treinador/{treinadorId}")
    @Operation(summary = "Listar turmas por ID do treinador", description = "Retorna todas as turmas ministradas por um treinador especÃ­fico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Turma>> buscarPorTreinadorId(@PathVariable int treinadorId) {
        return ResponseEntity.ok(turmaService.buscarPorTreinadorId(treinadorId));
    }
}

