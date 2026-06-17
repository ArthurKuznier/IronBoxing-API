package com.ironboxing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ironboxing.model.Treinador;
import java.util.Optional;
import java.util.List;

@Repository
public interface TreinadorRepository extends JpaRepository<Treinador, Integer> {

    Optional<Treinador> findByUsuarioEmail(String email);

    Optional<Treinador> findByUsuarioCpf(String cpf);

    Optional<Treinador> findByUsuarioTelefone(String telefone);

    List<Treinador> findByUsuarioGenero(String genero);

    List<Treinador> findByEspecialidade(String especialidade);

    Optional<Treinador> findByUsuarioId(Integer usuarioId);

    Optional<Treinador> findByCref(String cref);
}

