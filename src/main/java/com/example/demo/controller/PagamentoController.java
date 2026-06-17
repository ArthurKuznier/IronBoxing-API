package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import com.example.demo.model.Pagamento;
import com.example.demo.service.PagamentoService;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@Valid @RequestBody Pagamento pagamento) {
        Pagamento criado = pagamentoService.criarPagamento(pagamento);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Integer id,
            @Valid @RequestBody Pagamento pagamento) {
        pagamento.setId(id);
        Pagamento atualizado = pagamentoService.atualizarPagamento(pagamento);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> listarPagamentos() {
        return ResponseEntity.ok(pagamentoService.listarPagamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPorId(@PathVariable Integer id) {
        return pagamentoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Integer id) {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<Pagamento>> buscarPorMatriculaId(@PathVariable Integer matriculaId) {
        return ResponseEntity.ok(pagamentoService.buscarPorMatriculaId(matriculaId));
    }

    @GetMapping("/data/{dataPagamento}")
    public ResponseEntity<List<Pagamento>> buscarPorDataPagamento(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataPagamento) {
        return ResponseEntity.ok(pagamentoService.buscarPorDataPagamento(dataPagamento));
    }

    @GetMapping("/situacao/{situacao}")
    public ResponseEntity<List<Pagamento>> buscarPorSituacao(@PathVariable String situacao) {
        return ResponseEntity.ok(pagamentoService.buscarPorSituacao(situacao));
    }

    @GetMapping("/valor/{valorPago}")
    public ResponseEntity<List<Pagamento>> buscarPorValorPago(@PathVariable BigDecimal valorPago) {
        return ResponseEntity.ok(pagamentoService.buscarPorValorPago(valorPago));
    }

    @GetMapping("/forma/{formaPagamento}")
    public ResponseEntity<List<Pagamento>> buscarPorFormaPagamento(@PathVariable String formaPagamento) {
        return ResponseEntity.ok(pagamentoService.buscarPorFormaPagamento(formaPagamento));
    }
}
