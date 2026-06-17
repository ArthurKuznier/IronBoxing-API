package com.ironboxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.ironboxing.model.AvaliacaoFisica;
import com.ironboxing.service.AvaliacaoFisicaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/avaliacoes")
@Tag(name = "AvaliaÃ§Ãµes FÃ­sicas", description = "Endpoints de histÃ³rico e cadastro de avaliaÃ§Ãµes fÃ­sicas corporais")
public class AvaliacaoFisicaController {

    @Autowired
    private AvaliacaoFisicaService avaliacaoFisicaService;

    @PostMapping
    @Operation(summary = "Criar nova avaliaÃ§Ã£o fÃ­sica", description = "Cadastra uma nova avaliaÃ§Ã£o fÃ­sica para um atleta contendo peso, altura, IMC, etc. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "AvaliaÃ§Ã£o fÃ­sica criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<AvaliacaoFisica> criarAvaliacaoFisica(@Valid @RequestBody AvaliacaoFisica avaliacao) {
        AvaliacaoFisica criada = avaliacaoFisicaService.criarAvaliacaoFisica(avaliacao);
        return ResponseEntity.ok(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de uma avaliaÃ§Ã£o fÃ­sica", description = "Atualiza os dados de uma avaliaÃ§Ã£o cadastrada pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "AvaliaÃ§Ã£o fÃ­sica atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "AvaliaÃ§Ã£o fÃ­sica nÃ£o encontrada")
    })
    public ResponseEntity<AvaliacaoFisica> atualizarAvaliacaoFisica(@PathVariable Integer id,
            @Valid @RequestBody AvaliacaoFisica avaliacao) {
        avaliacao.setId(id);
        AvaliacaoFisica atualizada = avaliacaoFisicaService.atualizarAvaliacaoFisica(avaliacao);
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    @Operation(summary = "Listar todas as avaliaÃ§Ãµes fÃ­sicas", description = "Retorna a lista de todas as avaliaÃ§Ãµes fÃ­sicas cadastradas na base. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> listarAvaliacoesFisicas() {
        return ResponseEntity.ok(avaliacaoFisicaService.listarAvaliacoesFisicas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliaÃ§Ã£o fÃ­sica por ID", description = "Retorna os detalhes de uma avaliaÃ§Ã£o fÃ­sica especÃ­fica pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "AvaliaÃ§Ã£o fÃ­sica encontrada e retornada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "AvaliaÃ§Ã£o fÃ­sica nÃ£o encontrada")
    })
    public ResponseEntity<AvaliacaoFisica> buscarPorId(@PathVariable Integer id) {
        return avaliacaoFisicaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma avaliaÃ§Ã£o fÃ­sica", description = "Remove uma avaliaÃ§Ã£o fÃ­sica do sistema pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "AvaliaÃ§Ã£o fÃ­sica excluÃ­da com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Void> deletarAvaliacaoFisica(@PathVariable Integer id) {
        avaliacaoFisicaService.deletarAvaliacaoFisica(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/atleta/{atletaId}")
    @Operation(summary = "Listar avaliaÃ§Ãµes por ID do atleta", description = "Retorna o histÃ³rico de todas as avaliaÃ§Ãµes fÃ­sicas associadas a um atleta especÃ­fico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorAtletaId(@PathVariable Integer atletaId) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorAtletaId(atletaId));
    }

    @GetMapping("/data/{dataAvaliacao}")
    @Operation(summary = "Listar avaliaÃ§Ãµes por data", description = "Retorna as avaliaÃ§Ãµes fÃ­sicas realizadas em uma data especÃ­fica (formato YYYY-MM-DD). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorDataAvaliacao(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAvaliacao) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorDataAvaliacao(dataAvaliacao));
    }

    @GetMapping("/imc/{imc}")
    @Operation(summary = "Listar avaliaÃ§Ãµes por IMC", description = "Retorna as avaliaÃ§Ãµes fÃ­sicas com um valor especÃ­fico de IMC. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorImc(@PathVariable BigDecimal imc) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorImc(imc));
    }

    @GetMapping("/peso/{peso}")
    @Operation(summary = "Listar avaliaÃ§Ãµes por peso", description = "Retorna as avaliaÃ§Ãµes fÃ­sicas registradas com um peso especÃ­fico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorPeso(@PathVariable BigDecimal peso) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorPeso(peso));
    }

    @GetMapping("/altura/{altura}")
    @Operation(summary = "Listar avaliaÃ§Ãµes por altura", description = "Retorna as avaliaÃ§Ãµes fÃ­sicas registradas com uma altura especÃ­fica (em centÃ­metros). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<AvaliacaoFisica>> buscarPorAltura(@PathVariable Integer altura) {
        return ResponseEntity.ok(avaliacaoFisicaService.buscarPorAltura(altura));
    }
}

