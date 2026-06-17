package com.ironboxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.ironboxing.model.Matricula;
import com.ironboxing.service.MatriculaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/matriculas")
@Tag(name = "MatrÃ­culas", description = "Endpoints de gerenciamento e consulta de matrÃ­culas de atletas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PostMapping
    @Operation(summary = "Criar nova matrÃ­cula", description = "Registra a matrÃ­cula de um atleta em uma turma sob um determinado plano. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "MatrÃ­cula criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Matricula> criarMatricula(@Valid @RequestBody Matricula matricula) {
        Matricula criada = matriculaService.criarMatricula(matricula);
        return ResponseEntity.ok(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de uma matrÃ­cula", description = "Atualiza os dados de uma matrÃ­cula cadastrada pelo ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "MatrÃ­cula atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "MatrÃ­cula nÃ£o encontrada")
    })
    public ResponseEntity<Matricula> atualizarMatricula(@PathVariable Integer id,
            @Valid @RequestBody Matricula matricula) {
        matricula.setId(id);
        Matricula atualizada = matriculaService.atualizarMatricula(matricula);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    @Operation(summary = "Listar todas as matrÃ­culas", description = "Retorna a lista de todas as matrÃ­culas cadastradas. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Matricula>> listarMatriculas() {
        return ResponseEntity.ok(matriculaService.listarMatriculas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar matrÃ­cula por ID", description = "Retorna os detalhes de uma matrÃ­cula especÃ­fica pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "MatrÃ­cula retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "MatrÃ­cula nÃ£o encontrada")
    })
    public ResponseEntity<Matricula> buscarPorId(@PathVariable Integer id) {
        return matriculaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma matrÃ­cula", description = "Remove uma matrÃ­cula do sistema pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "MatrÃ­cula excluÃ­da com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Void> deletarMatricula(@PathVariable Integer id) {
        matriculaService.deletarMatricula(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/atleta/{atletaId}")
    @Operation(summary = "Listar matrÃ­culas por ID do atleta", description = "Retorna a lista de todas as matrÃ­culas associadas a um atleta especÃ­fico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Matricula>> buscarPorAtletaId(@PathVariable Integer atletaId) {
        return ResponseEntity.ok(matriculaService.buscarPorAtletaId(atletaId));
    }

    @GetMapping("/plano/{planoId}")
    @Operation(summary = "Listar matrÃ­culas por ID do plano", description = "Retorna a lista de todas as matrÃ­culas associadas a um plano especÃ­fico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Matricula>> buscarPorPlanoId(@PathVariable Integer planoId) {
        return ResponseEntity.ok(matriculaService.buscarPorPlanoId(planoId));
    }

    @GetMapping("/situacao/{situacaoMat}")
    @Operation(summary = "Listar matrÃ­culas por situaÃ§Ã£o", description = "Retorna as matrÃ­culas de acordo com a situaÃ§Ã£o (ex: ATIVA, INATIVA, CANCELADA). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Matricula>> buscarPorSituacaoMat(@PathVariable String situacaoMat) {
        return ResponseEntity.ok(matriculaService.buscarPorSituacaoMat(situacaoMat));
    }

    @GetMapping("/data/{dataMatricula}")
    @Operation(summary = "Listar matrÃ­culas por data", description = "Retorna as matrÃ­culas realizadas em uma data especÃ­fica (formato YYYY-MM-DD). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Matricula>> buscarPorDataMatricula(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMatricula) {
        return ResponseEntity.ok(matriculaService.buscarPorDataMatricula(dataMatricula));
    }

    @GetMapping("/turma/{turmaId}")
    @Operation(summary = "Listar matrÃ­culas por ID da turma", description = "Retorna todas as matrÃ­culas vinculadas a uma turma especÃ­fica. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Matricula>> buscarPorTurmaId(@PathVariable Integer turmaId) {
        return ResponseEntity.ok(matriculaService.buscarPorTurmaId(turmaId));
    }
}

