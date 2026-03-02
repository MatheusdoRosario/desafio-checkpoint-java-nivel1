package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroSalaDTO(@NotBlank String nome,
                              @NotNull int capacidade) {
}
