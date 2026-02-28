package br.com.rosario.desafio_checkpoint_java_nivel1.dto;

import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioDTO(@NotBlank String nome,
                         @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$\n") String telefone) {

    public UsuarioDTO(Usuario usuario) {
        this(usuario.getNome(), usuario.getTelefone());
    }
}
