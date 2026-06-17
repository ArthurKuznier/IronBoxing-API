package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.demo.model.Treinador;
import com.example.demo.service.TreinadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/treinadores")
@Tag(name = "Treinadores", description = "Endpoints de gerenciamento e consulta de perfis de treinadores")
public class TreinadorController {

    @Autowired
    private TreinadorService treinadorService;

    @PostMapping
    @Operation(summary = "Criar perfil de treinador", description = "Associa um perfil de treinador (com CREF e especialidade) a um usuário existente. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil de treinador criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Campos inválidos ou CREF/Usuário já associado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Treinador> criarTreinador(@Valid @RequestBody Treinador treinador) {
        Treinador criado = treinadorService.criarTreinador(treinador);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar perfil de treinador", description = "Atualiza os dados de CREF e especialidade de um treinador existente pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou CREF duplicado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Treinador> atualizarTreinador(@PathVariable Integer id,
            @Valid @RequestBody Treinador treinador) {
        treinador.setId(id);
        Treinador atualizado = treinadorService.atualizarTreinador(treinador);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os treinadores", description = "Retorna a lista de todos os treinadores e seus respectivos dados de usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Treinador>> listarTreinadores() {
        return ResponseEntity.ok(treinadorService.listarTreinadores());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar treinador por ID", description = "Retorna os detalhes de um treinador pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Treinador retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Treinador não encontrado")
    })
    public ResponseEntity<Treinador> buscarPorId(@PathVariable Integer id) {
        return treinadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar perfil de treinador por ID", description = "Remove o perfil de um treinador sem necessariamente excluir seu usuário de base. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Perfil removido com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        treinadorService.deletarTreinador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cref/{cref}")
    @Operation(summary = "Buscar treinador por CREF", description = "Localiza o perfil de um treinador utilizando seu registro profissional de CREF. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Treinador localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Treinador não encontrado com o CREF informado")
    })
    public ResponseEntity<Treinador> buscarPorCref(@PathVariable String cref) {
        return treinadorService.buscarPorCref(cref).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar treinador por e-mail", description = "Localiza o perfil de um treinador pelo endereço de e-mail do seu usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Treinador localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Treinador não encontrado")
    })
    public ResponseEntity<Treinador> buscarPorEmail(@PathVariable String email) {
        return treinadorService.buscarPorEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar treinador por CPF", description = "Localiza o perfil de um treinador pelo CPF do seu usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Treinador localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Treinador não encontrado")
    })
    public ResponseEntity<Treinador> buscarPorCpf(@PathVariable String cpf) {
        return treinadorService.buscarPorCpf(cpf).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefone/{telefone}")
    @Operation(summary = "Buscar treinador por telefone", description = "Localiza o perfil de um treinador pelo telefone do seu usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Treinador localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Treinador não encontrado")
    })
    public ResponseEntity<Treinador> buscarPorTelefone(@PathVariable String telefone) {
        return treinadorService.buscarPorTelefone(telefone).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genero/{genero}")
    @Operation(summary = "Listar treinadores por gênero", description = "Retorna uma lista de treinadores baseada no gênero associado (M/F/O). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Treinador>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(treinadorService.buscarPorGenero(genero));
    }

    @GetMapping("/especialidade/{especialidade}")
    @Operation(summary = "Listar treinadores por especialidade", description = "Retorna uma lista de treinadores filtrados pela sua especialidade de atuação (ex: Muay Thai, Boxe). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Treinador>> buscarPorEspecialidade(@PathVariable String especialidade) {
        return ResponseEntity.ok(treinadorService.buscarPorEspecialidade(especialidade));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar treinador por ID do Usuário", description = "Localiza o perfil do treinador associado ao ID da conta de usuário. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Treinador localizado e retornado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Treinador não encontrado para o usuário informado")
    })
    public ResponseEntity<Treinador> buscarPorUsuarioId(@PathVariable Integer usuarioId) {
        return treinadorService.buscarPorUsuarioId(usuarioId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
