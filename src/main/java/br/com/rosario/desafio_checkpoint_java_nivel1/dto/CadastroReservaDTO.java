package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastroReservaDTO(@NotNull Sala sala,
                                 @NotNull Usuario usuario) {
}
