package com.ironboxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.ironboxing.security.JwtService;
import jakarta.validation.Valid;
import com.ironboxing.model.Usuario;
import com.ironboxing.model.UserRole;
import com.ironboxing.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "UsuÃ¡rios", description = "Endpoints de gerenciamento de usuÃ¡rios, cadastro e login pÃºblico")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    @Operation(summary = "Cadastrar um novo usuÃ¡rio", description = "Cria um novo usuÃ¡rio na base de dados com senha criptografada via BCrypt. Rota pÃºblica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "UsuÃ¡rio cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erros de validaÃ§Ã£o nos campos informados ou dados cadastrais jÃ¡ existentes")
    })
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario criado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(criado);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuÃ¡rio", description = "Realiza o login com e-mail e senha, gerando um token JWT de acesso. Rota pÃºblica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso, retorna o token de acesso e os dados do usuÃ¡rio"),
        @ApiResponse(responseCode = "401", description = "Credenciais invÃ¡lidas (e-mail ou senha incorretos)")
    })
    public ResponseEntity<Map<String, Object>> entrarUsuario(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("UsuÃ¡rio nÃ£o encontrado"));

        String token = jwtService.generateToken(userDetails);
        
        usuario.setSenha(null);

        Map<String, Object> response = Map.of(
                "token", token,
                "usuario", usuario
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um usuÃ¡rio", description = "Atualiza os dados cadastrais de um usuÃ¡rio existente pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "UsuÃ¡rio atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados invÃ¡lidos fornecidos ou dados duplicados com outro usuÃ¡rio"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario atualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuÃ¡rios", description = "Retorna uma lista completa contendo todos os usuÃ¡rios cadastrados na base de dados. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuÃ¡rio por ID", description = "Busca os detalhes cadastrais de um usuÃ¡rio especÃ­fico utilizando seu identificador numÃ©rico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "UsuÃ¡rio encontrado e retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "UsuÃ¡rio nÃ£o encontrado com o ID informado")
    })
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuÃ¡rio por ID", description = "Remove definitivamente um usuÃ¡rio do sistema com base no seu identificador. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "UsuÃ¡rio removido com sucesso (No Content)"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuÃ¡rio por e-mail", description = "Busca um usuÃ¡rio cadastrado pelo seu endereÃ§o de e-mail. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "UsuÃ¡rio localizado e retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "UsuÃ¡rio nÃ£o encontrado com o e-mail informado")
    })
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar usuÃ¡rio por CPF", description = "Busca um usuÃ¡rio pelo nÃºmero do seu documento de CPF. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "UsuÃ¡rio localizado e retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "UsuÃ¡rio nÃ£o encontrado com o CPF informado")
    })
    public ResponseEntity<Usuario> buscarPorCpf(@PathVariable String cpf) {
        return usuarioService.buscarPorCpf(cpf).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefone/{telefone}")
    @Operation(summary = "Buscar usuÃ¡rio por telefone", description = "Busca um usuÃ¡rio pelo seu nÃºmero de telefone de contato. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "UsuÃ¡rio localizado e retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido"),
        @ApiResponse(responseCode = "404", description = "UsuÃ¡rio nÃ£o encontrado com o telefone informado")
    })
    public ResponseEntity<Usuario> buscarPorTelefone(@PathVariable String telefone) {
        return usuarioService.buscarPorTelefone(telefone).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genero/{genero}")
    @Operation(summary = "Listar usuÃ¡rios por gÃªnero", description = "Retorna uma lista de usuÃ¡rios filtrados pelo caractere correspondente ao seu gÃªnero (M/F/O). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuÃ¡rios retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Usuario>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(usuarioService.buscarPorGenero(genero));
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Listar usuÃ¡rios por papel (Role)", description = "Retorna uma lista de usuÃ¡rios filtrados pelo seu papel de acesso (ADMIN/ATLETA/TREINADOR). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuÃ¡rios retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT vÃ¡lido")
    })
    public ResponseEntity<List<Usuario>> buscarPorRole(@PathVariable UserRole.Role role) {
        return ResponseEntity.ok(usuarioService.buscarPorRole(role));
    }
}
