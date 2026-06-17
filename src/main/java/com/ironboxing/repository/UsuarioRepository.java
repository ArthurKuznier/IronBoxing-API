package com.ironboxing.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ironboxing.model.Usuario;
import com.ironboxing.model.UserRole;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByTelefone(String telefone);

    List<Usuario> findByGenero(String genero);

    List<Usuario> findByRole(UserRole.Role role);

}

