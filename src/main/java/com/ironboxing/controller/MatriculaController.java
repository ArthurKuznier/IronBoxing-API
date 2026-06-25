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
@Tag(name = "Matrículas", description = "Endpoints de gerenciamento e consulta de matrículas de atletas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PostMapping
    @Operation(summary = "Criar nova matrícula", description = "Registra a matrícula de um atleta em uma turma sob um determinado plano. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Matrícula criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Matricula> criarMatricula(@Valid @RequestBody Matricula matricula) {
        Matricula criada = matriculaService.criarMatricula(matricula);
        return ResponseEntity.ok(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de uma matrícula", description = "Atualiza os dados de uma matrícula cadastrada pelo ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Matrícula atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    })
    public ResponseEntity<Matricula> atualizarMatricula(@PathVariable Integer id,
            @Valid @RequestBody Matricula matricula) {
        matricula.setId(id);
        Matricula atualizada = matriculaService.atualizarMatricula(matricula);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    @Operation(summary = "Listar todas as matrículas", description = "Retorna a lista de todas as matrículas cadastradas. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Matricula>> listarMatriculas() {
        return ResponseEntity.ok(matriculaService.listarMatriculas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar matrícula por ID", description = "Retorna os detalhes de uma matrícula específica pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Matrícula retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    })
    public ResponseEntity<Matricula> buscarPorId(@PathVariable Integer id) {
        return matriculaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma matrícula", description = "Remove uma matrícula do sistema pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Matrícula excluída com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Void> deletarMatricula(@PathVariable Integer id) {
        matriculaService.deletarMatricula(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/atleta/{atletaId}")
    @Operation(summary = "Listar matrículas por ID do atleta", description = "Retorna a lista de todas as matrículas associadas a um atleta específico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Matricula>> buscarPorAtletaId(@PathVariable Integer atletaId) {
        return ResponseEntity.ok(matriculaService.buscarPorAtletaId(atletaId));
    }

    @GetMapping("/plano/{planoId}")
    @Operation(summary = "Listar matrículas por ID do plano", description = "Retorna a lista de todas as matrículas associadas a um plano específico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Matricula>> buscarPorPlanoId(@PathVariable Integer planoId) {
        return ResponseEntity.ok(matriculaService.buscarPorPlanoId(planoId));
    }

    @GetMapping("/situacao/{situacaoMat}")
    @Operation(summary = "Listar matrículas por situação", description = "Retorna as matrículas de acordo com a situação (ex: ATIVA, INATIVA, CANCELADA). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Matricula>> buscarPorSituacaoMat(@PathVariable String situacaoMat) {
        return ResponseEntity.ok(matriculaService.buscarPorSituacaoMat(situacaoMat));
    }

    @GetMapping("/data/{dataMatricula}")
    @Operation(summary = "Listar matrículas por data", description = "Retorna as matrículas realizadas em uma data específica (formato YYYY-MM-DD). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Matricula>> buscarPorDataMatricula(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMatricula) {
        return ResponseEntity.ok(matriculaService.buscarPorDataMatricula(dataMatricula));
    }

    @GetMapping("/turma/{turmaId}")
    @Operation(summary = "Listar matrículas por ID da turma", description = "Retorna todas as matrículas vinculadas a uma turma específica. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Matricula>> buscarPorTurmaId(@PathVariable Integer turmaId) {
        return ResponseEntity.ok(matriculaService.buscarPorTurmaId(turmaId));
    }
}

