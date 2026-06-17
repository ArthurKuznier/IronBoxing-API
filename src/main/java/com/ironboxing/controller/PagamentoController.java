package com.ironboxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.ironboxing.model.Pagamento;
import com.ironboxing.service.PagamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamentos", description = "Endpoints de fluxo de pagamentos e histÃ³rico financeiro")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    @Operation(summary = "Criar novo pagamento", description = "Registra a transaÃ§Ã£o de um pagamento relacionado a uma matrÃ­cula. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pagamento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Pagamento> criarPagamento(@Valid @RequestBody Pagamento pagamento) {
        Pagamento criado = pagamentoService.criarPagamento(pagamento);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um pagamento", description = "Atualiza os dados de um pagamento cadastrado pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "Pagamento nÃ£o encontrado")
    })
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Integer id,
            @Valid @RequestBody Pagamento pagamento) {
        pagamento.setId(id);
        Pagamento atualizado = pagamentoService.atualizarPagamento(pagamento);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pagamentos", description = "Retorna a lista de todos os pagamentos cadastrados. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Pagamento>> listarPagamentos() {
        return ResponseEntity.ok(pagamentoService.listarPagamentos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID", description = "Retorna os detalhes de um pagamento especÃ­fico pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pagamento retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "Pagamento nÃ£o encontrado")
    })
    public ResponseEntity<Pagamento> buscarPorId(@PathVariable Integer id) {
        return pagamentoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um pagamento", description = "Remove um pagamento do sistema pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pagamento excluÃ­do com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Void> deletarPagamento(@PathVariable Integer id) {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/matricula/{matriculaId}")
    @Operation(summary = "Listar pagamentos por ID da matrÃ­cula", description = "Retorna todos os pagamentos associados a uma matrÃ­cula especÃ­fica. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Pagamento>> buscarPorMatriculaId(@PathVariable Integer matriculaId) {
        return ResponseEntity.ok(pagamentoService.buscarPorMatriculaId(matriculaId));
    }

    @GetMapping("/data/{dataPagamento}")
    @Operation(summary = "Listar pagamentos por data/hora", description = "Retorna os pagamentos realizados em uma data/hora especÃ­fica (formato YYYY-MM-DDTHH:mm:ss). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Pagamento>> buscarPorDataPagamento(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataPagamento) {
        return ResponseEntity.ok(pagamentoService.buscarPorDataPagamento(dataPagamento));
    }

    @GetMapping("/situacao/{situacao}")
    @Operation(summary = "Listar pagamentos por situaÃ§Ã£o", description = "Retorna os pagamentos filtrados pela situaÃ§Ã£o (ex: PAGO, PENDENTE, ATRASADO). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Pagamento>> buscarPorSituacao(@PathVariable String situacao) {
        return ResponseEntity.ok(pagamentoService.buscarPorSituacao(situacao));
    }

    @GetMapping("/valor/{valorPago}")
    @Operation(summary = "Listar pagamentos por valor", description = "Retorna os pagamentos filtrados pelo valor pago especÃ­fico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Pagamento>> buscarPorValorPago(@PathVariable BigDecimal valorPago) {
        return ResponseEntity.ok(pagamentoService.buscarPorValorPago(valorPago));
    }

    @GetMapping("/forma/{formaPagamento}")
    @Operation(summary = "Listar pagamentos por forma de pagamento", description = "Retorna os pagamentos filtrados pela forma de pagamento (ex: CARTAO, DINHEIRO, PIX). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Pagamento>> buscarPorFormaPagamento(@PathVariable String formaPagamento) {
        return ResponseEntity.ok(pagamentoService.buscarPorFormaPagamento(formaPagamento));
    }
}

