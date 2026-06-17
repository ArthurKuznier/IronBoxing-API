package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.model.Usuario;
import java.util.List;
import java.util.Optional;
import com.example.demo.model.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usuario criarUsuario(Usuario usuario) {
        Optional<Usuario> emailExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (emailExistente.isPresent()) {
            throw new RuntimeException("E-mail já cadastrado, faça o login");
        }
        Optional<Usuario> cpfExistente = usuarioRepository.findByCpf(usuario.getCpf());
        if (cpfExistente.isPresent()) {
            throw new RuntimeException("Erro ao salvar usuario, caso já tenha conta faça o login");
        }
        Optional<Usuario> telefoneExistente = usuarioRepository.findByTelefone(usuario.getTelefone());
        if (telefoneExistente.isPresent()) {
            throw new RuntimeException("Telefone já cadastrado, faça o login");
        }
        String senhaHash = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaHash);
        return usuarioRepository.save(usuario);
    }

    public Usuario entrarUsuario(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (!usuario.isPresent()) {
            throw new RuntimeException("Email ou senha invalidos");
        }
        if (encoder.matches(senha, usuario.get().getSenha())) {
            return usuario.get();
        }
        throw new RuntimeException("Email ou senha invalidos");
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public void deletarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        Optional<Usuario> emailExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (emailExistente.isPresent() && !emailExistente.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("E-mail já cadastrado, faça o login");
        }
        Optional<Usuario> cpfExistente = usuarioRepository.findByCpf(usuario.getCpf());
        if (cpfExistente.isPresent() && !cpfExistente.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("Erro ao salvar usuario, caso já tenha conta faça o login");
        }
        Optional<Usuario> telefoneExistente = usuarioRepository.findByTelefone(usuario.getTelefone());
        if (telefoneExistente.isPresent() && !telefoneExistente.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("Telefone já cadastrado, faça o login");
        }
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public Optional<Usuario> buscarPorTelefone(String telefone) {
        return usuarioRepository.findByTelefone(telefone);
    }

    public List<Usuario> buscarPorGenero(String genero) {
        return usuarioRepository.findByGenero(genero);
    }

    public List<Usuario> buscarPorRole(UserRole.Role role) {
        return usuarioRepository.findByRole(role);
    }

}
