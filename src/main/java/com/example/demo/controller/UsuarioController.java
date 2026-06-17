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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario criado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(criado);
    }

    @PostMapping("/login")
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
        
        // Ocultar a senha no retorno
        usuario.setSenha(null);

        Map<String, Object> response = Map.of(
                "token", token,
                "usuario", usuario
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario atualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Usuario> buscarPorCpf(@PathVariable String cpf) {
        return usuarioService.buscarPorCpf(cpf).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<Usuario> buscarPorTelefone(@PathVariable String telefone) {
        return usuarioService.buscarPorTelefone(telefone).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Usuario>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(usuarioService.buscarPorGenero(genero));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Usuario>> buscarPorRole(@PathVariable UserRole.Role role) {
        return ResponseEntity.ok(usuarioService.buscarPorRole(role));
    }
}