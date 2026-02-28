package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Reserva;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservaDTO(LocalDate inicio,
                         @NotNull Sala sala,
                         @NotNull Usuario usuario) {
    public ReservaDTO(Reserva reserva) {
        this(reserva.getInicio(), reserva.getSala(), reserva.getUsuario());
    }
}
