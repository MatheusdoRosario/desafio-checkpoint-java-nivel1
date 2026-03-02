package br.com.rosario.desafio_checkpoint_java_nivel1.repository;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    Page<Reserva> findBySalaId(Long id, Pageable pageable);

    Page<Reserva> findByUsuarioId(Long id, Pageable pageable);

    @Query("""
        SELECT CASE WHEN COUNT(r) >= s.capacidade THEN true ELSE false END
        FROM Reserva r
        JOIN r.sala s
        WHERE s.id = :salaId
          AND r.id <> :reservaId
    """)
    Boolean isCapacidadeAtingidaExcluindoReserva(@Param("salaId") Long salaId,
                                                 @Param("reservaId") UUID reservaId);

    Long countBySalaId(Long id);
}
