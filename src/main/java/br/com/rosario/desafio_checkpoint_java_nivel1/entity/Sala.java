package br.com.rosario.desafio_checkpoint_java_nivel1.entity;

import jakarta.persistence.*;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int capacidade;

    private int reservas;

    @Enumerated(EnumType.STRING)
    private StatusSala statusSala;

    public Sala() {
    }

    public Sala(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.statusSala = StatusSala.DISPONIVEL;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getReservas() {
        return reservas;
    }

    public StatusSala getStatusSala() {
        return statusSala;
    }

    void desativarSala() {
        this.statusSala = StatusSala.INATIVA;
    }

    void ativarSala() {
        this.statusSala = StatusSala.DISPONIVEL;
    }

    void reservar() {
        this.reservas += 1;
    }

    void cancelarReserva() {
        this.reservas -= 1;
    }
}
