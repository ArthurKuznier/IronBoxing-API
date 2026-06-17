package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.example.demo.model.AvaliacaoFisica;
import com.example.demo.service.AvaliacaoFisicaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/avaliacoes")
@Tag(name = "Avaliações Físicas", description = "Endpoints de histórico e cadastro de avaliações físicas corporais")
public class AvaliacaoFisicaController {

    @Autowired
    private AvaliacaoFisicaService avaliacaoFisicaService;

    @PostMapping
    @Operation(summary = "Criar nova avaliação física", description = "Cadastra uma nova avaliação física para um atleta contendo peso, altura, IMC, etc. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Avaliação física criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<AvaliacaoFisica> criarAvaliacaoFisica(@Valid @RequestBody AvaliacaoFisica avaliacao) {
        AvaliacaoFisica criada = avaliacaoFisicaService.criarAvaliacaoFisica(avaliacao);
        return ResponseEntity.ok(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de uma avaliação física", description = "Atualiza os dados de uma avaliação cadastrada pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Avaliação física atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Avaliação física não encontrada")
    })
    public ResponseEntity<AvaliacaoFisica> atualizarAvaliacaoFisica(@PathVariable Integer id,
            @Valid @RequestBody AvaliacaoFisica avaliacao) {
        avaliacao.setId(id);
        AvaliacaoFisica atualizada = avaliacaoFisicaService.atualizarAvaliacaoFisica(avaliacao);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    @Operation(summary = "Listar todas as avaliações físicas", description = "Retorna a lista de todas as avaliações físicas cadastradas na base. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> listarAvaliacoesFisicas() {
        return ResponseEntity.ok(avaliacaoFisicaService.listarAvaliacoesFisicas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação física por ID", description = "Retorna os detalhes de uma avaliação física específica pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Avaliação física encontrada e retornada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Avaliação física não encontrada")
    })
    public ResponseEntity<AvaliacaoFisica> buscarPorId(@PathVariable Integer id) {
        return avaliacaoFisicaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma avaliação física", description = "Remove uma avaliação física do sistema pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Avaliação física excluída com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Void> deletarAvaliacaoFisica(@PathVariable Integer id) {
        avaliacaoFisicaService.deletarAvaliacaoFisica(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/atleta/{atletaId}")
    @Operation(summary = "Listar avaliações por ID do atleta", description = "Retorna o histórico de todas as avaliações físicas associadas a um atleta específico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorAtletaId(@PathVariable Integer atletaId) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorAtletaId(atletaId));
    }

    @GetMapping("/data/{dataAvaliacao}")
    @Operation(summary = "Listar avaliações por data", description = "Retorna as avaliações físicas realizadas em uma data específica (formato YYYY-MM-DD). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorDataAvaliacao(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAvaliacao) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorDataAvaliacao(dataAvaliacao));
    }

    @GetMapping("/imc/{imc}")
    @Operation(summary = "Listar avaliações por IMC", description = "Retorna as avaliações físicas com um valor específico de IMC. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorImc(@PathVariable BigDecimal imc) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorImc(imc));
    }

    @GetMapping("/peso/{peso}")
    @Operation(summary = "Listar avaliações por peso", description = "Retorna as avaliações físicas registradas com um peso específico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorPeso(@PathVariable BigDecimal peso) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorPeso(peso));
    }

    @GetMapping("/altura/{altura}")
    @Operation(summary = "Listar avaliações por altura", description = "Retorna as avaliações físicas registradas com uma altura específica (em centímetros). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorAltura(@PathVariable Integer altura) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorAltura(altura));
    }
}
