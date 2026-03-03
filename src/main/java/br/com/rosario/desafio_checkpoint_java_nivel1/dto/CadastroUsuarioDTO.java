package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastroUsuarioDTO(@NotBlank String nome,
                                 @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$") String telefone) {
}
