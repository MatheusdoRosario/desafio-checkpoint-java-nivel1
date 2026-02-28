package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import jakarta.validation.constraints.Pattern;

public record CadastroUsuarioDTO(String nome,
                                 @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$\n") String telefone) {
}
