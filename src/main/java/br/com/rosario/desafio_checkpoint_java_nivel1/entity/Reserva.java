package br.com.rosario.desafio_checkpoint_java_nivel1.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private StatusReserva statusReserva;

    public Reserva() {
    }

    public Reserva(Sala sala, Usuario usuario) {
        this.sala = sala;
        this.usuario = usuario;
        this.statusReserva = StatusReserva.ATIVA;
        sala.reservar();
    }

    public UUID getId() {
        return id;
    }

    public Sala getSala() {
        return sala;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public StatusReserva getStatusReserva() {
        return statusReserva;
    }

    void cancelarReserva() {
        this.statusReserva = StatusReserva.CANCELADA;
        sala.cancelarReserva();
    }
}
