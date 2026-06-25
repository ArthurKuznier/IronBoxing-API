package com.ironboxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.ironboxing.model.Atleta;
import com.ironboxing.service.AtletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ironboxing.exception.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/atletas")
@Tag(name = "Atletas", description = "Endpoints de gerenciamento e consulta de perfis de atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

    @PostMapping
    @Operation(summary = "Criar perfil de atleta", description = "Associa um perfil de atleta a uma conta de usuário existente. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil de atleta criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos ou Usuário já associado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Atleta> criarAtleta(@Valid @RequestBody Atleta atleta) {
        Atleta criado = atletaService.criarAtleta(atleta);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar perfil de atleta", description = "Atualiza os dados de um perfil de atleta específico pelo ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Atleta> atualizarAtleta(@PathVariable Integer id, @Valid @RequestBody Atleta atleta) {
        atleta.setId(id);
        Atleta atualizado = atletaService.atualizarAtleta(atleta);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os atletas", description = "Retorna a lista de todos os atletas cadastrados e seus respectivos dados de usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Atleta>> listarAtletas() {
        return ResponseEntity.ok(atletaService.listarAtletas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar atleta por ID", description = "Retorna os detalhes de um perfil de atleta pelo seu identificador. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Atleta retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Atleta não encontrado")
    })
    public ResponseEntity<Atleta> buscarPorId(@PathVariable Integer id) {
        Atleta atleta = atletaService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Atleta não encontrado com o ID: " + id));
        return ResponseEntity.ok(atleta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar perfil de atleta por ID", description = "Remove o perfil de um atleta pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Perfil removido com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Void> deletarAtleta(@PathVariable Integer id) {
        atletaService.deletarAtleta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar atleta por e-mail", description = "Localiza o atleta pelo e-mail do seu usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Atleta localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Atleta não encontrado com o e-mail informado")
    })
    public ResponseEntity<Atleta> buscarPorEmail(@PathVariable String email) {
        Atleta atleta = atletaService.buscarPorEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Atleta não encontrado com o e-mail: " + email));
        return ResponseEntity.ok(atleta);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar atleta por CPF", description = "Localiza o atleta pelo CPF do seu usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Atleta localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Atleta não encontrado com o CPF informado")
    })
    public ResponseEntity<Atleta> buscarPorCpf(@PathVariable String cpf) {
        Atleta atleta = atletaService.buscarPorCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Atleta não encontrado com o CPF: " + cpf));
        return ResponseEntity.ok(atleta);
    }

    @GetMapping("/telefone/{telefone}")
    @Operation(summary = "Buscar atleta por telefone", description = "Localiza o atleta pelo telefone do seu usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Atleta localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Atleta não encontrado com o telefone informado")
    })
    public ResponseEntity<Atleta> buscarPorTelefone(@PathVariable String telefone) {
        Atleta atleta = atletaService.buscarPorTelefone(telefone)
                .orElseThrow(() -> new EntityNotFoundException("Atleta não encontrado com o telefone: " + telefone));
        return ResponseEntity.ok(atleta);
    }

    @GetMapping("/genero/{genero}")
    @Operation(summary = "Listar atletas por gênero", description = "Retorna uma lista de atletas baseada no gênero associado (M/F/O). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Atleta>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(atletaService.buscarPorGenero(genero));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar atleta por ID do Usuário", description = "Localiza o perfil de atleta associado ao ID da conta de usuário correspondente. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Atleta localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Atleta não encontrado para o usuário informado")
    })
    public ResponseEntity<Atleta> buscarPorUsuarioId(@PathVariable Integer usuarioId) {
        Atleta atleta = atletaService.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Atleta não encontrado para o usuário com o ID: " + usuarioId));
        return ResponseEntity.ok(atleta);
    }
}

