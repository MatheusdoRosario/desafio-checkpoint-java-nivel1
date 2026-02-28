package br.com.rosario.desafio_checkpoint_java_nivel1.entity;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoUsuarioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    public Usuario() {
    }

    public Usuario(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void atualizarDados(AtualizacaoUsuarioDTO dto) {
        this.id = dto.id();
        this.nome = dto.nome();
        this.telefone = dto.telefone();
    }
}
