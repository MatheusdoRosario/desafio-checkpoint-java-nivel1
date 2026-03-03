package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.StatusReserva;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;

import java.time.LocalDate;
import java.util.UUID;

public record AtualizaReservaDTO(UUID id,
                                 LocalDate inicio,
                                 Sala sala,
                                 Usuario usuario,
                                 StatusReserva statusReserva) {
}
