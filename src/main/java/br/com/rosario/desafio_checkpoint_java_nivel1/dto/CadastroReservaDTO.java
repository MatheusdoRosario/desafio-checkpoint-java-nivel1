package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.StatusReserva;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;

import java.time.LocalDate;

public record CadastroReservaDTO(LocalDate inicio,
                                 Sala sala,
                                 Usuario usuario,
                                 StatusReserva statusReserva) {
}
