package br.com.rosario.desafio_checkpoint_java_nivel1.repository;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.StatusSala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    Boolean existsByNome(String nome);

    Boolean existsByNomeAndIdNot(String nome, Long id);

    Boolean existsByIdAndStatusSala(Long id, StatusSala statusSala);

}
