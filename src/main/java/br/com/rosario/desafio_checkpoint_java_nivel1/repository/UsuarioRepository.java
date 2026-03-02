package br.com.rosario.desafio_checkpoint_java_nivel1.repository;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Boolean existsByTelefone(String telefone);
}
