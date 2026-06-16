package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Atleta;
import java.util.Optional;
import java.util.List;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Integer> {

    Optional<Atleta> findByUsuarioEmail(String email);

    Optional<Atleta> findByUsuarioCpf(String cpf);

    Optional<Atleta> findByUsuarioTelefone(String telefone);

    List<Atleta> findByUsuarioGenero(String genero);

    Optional<Atleta> findByUsuarioId(Integer usuarioId);
}
