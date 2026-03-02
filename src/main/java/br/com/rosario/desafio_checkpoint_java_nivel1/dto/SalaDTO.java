package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Reserva;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SalaDTO(@NotBlank String nome,
                      @NotBlank int capacidade,
                      List<Reserva> reservas) {

    public SalaDTO(Sala sala) {
        this(sala.getNome(), sala.getCapacidade(), sala.getReservas());
    }
}
