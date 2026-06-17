package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.security.JwtService;
import jakarta.validation.Valid;
import com.example.demo.model.Usuario;
import com.example.demo.model.UserRole;
import com.example.demo.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários, cadastro e login público")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    @Operation(summary = "Cadastrar um novo usuário", description = "Cria um novo usuário na base de dados com senha criptografada via BCrypt. Rota pública.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erros de validação nos campos informados ou dados cadastrais já existentes")
    })
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario criado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(criado);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Realiza o login com e-mail e senha, gerando um token JWT de acesso. Rota pública.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso, retorna o token de acesso e os dados do usuário"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas (e-mail ou senha incorretos)")
    })
    public ResponseEntity<Map<String, Object>> entrarUsuario(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtService.generateToken(userDetails);
        
        usuario.setSenha(null);

        Map<String, Object> response = Map.of(
                "token", token,
                "usuario", usuario
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um usuário", description = "Atualiza os dados cadastrais de um usuário existente pelo seu ID. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos ou dados duplicados com outro usuário"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario atualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista completa contendo todos os usuários cadastrados na base de dados. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Busca os detalhes cadastrais de um usuário específico utilizando seu identificador numérico. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado e retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID informado")
    })
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário por ID", description = "Remove definitivamente um usuário do sistema com base no seu identificador. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso (No Content)"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por e-mail", description = "Busca um usuário cadastrado pelo seu endereço de e-mail. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário localizado e retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o e-mail informado")
    })
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar usuário por CPF", description = "Busca um usuário pelo número do seu documento de CPF. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário localizado e retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o CPF informado")
    })
    public ResponseEntity<Usuario> buscarPorCpf(@PathVariable String cpf) {
        return usuarioService.buscarPorCpf(cpf).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefone/{telefone}")
    @Operation(summary = "Buscar usuário por telefone", description = "Busca um usuário pelo seu número de telefone de contato. Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário localizado e retornado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o telefone informado")
    })
    public ResponseEntity<Usuario> buscarPorTelefone(@PathVariable String telefone) {
        return usuarioService.buscarPorTelefone(telefone).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genero/{genero}")
    @Operation(summary = "Listar usuários por gênero", description = "Retorna uma lista de usuários filtrados pelo caractere correspondente ao seu gênero (M/F/O). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Usuario>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(usuarioService.buscarPorGenero(genero));
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Listar usuários por papel (Role)", description = "Retorna uma lista de usuários filtrados pelo seu papel de acesso (ADMIN/ATLETA/TREINADOR). Rota protegida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - requer token JWT válido")
    })
    public ResponseEntity<List<Usuario>> buscarPorRole(@PathVariable UserRole.Role role) {
        return ResponseEntity.ok(usuarioService.buscarPorRole(role));
    }
}