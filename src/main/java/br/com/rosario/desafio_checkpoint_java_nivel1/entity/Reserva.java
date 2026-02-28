package br.com.rosario.desafio_checkpoint_java_nivel1.entity;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate inicio;

    private LocalDate fim;

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
        this.inicio = LocalDate.now();
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

    public void cancelarReserva() {
        this.statusReserva = StatusReserva.CANCELADA;
        sala.cancelarReserva();
    }

    public void adicionarFimDaReserva(LocalDate date) {
        if (date.isBefore(this.inicio)){
            throw new DateTimeException("A data de fim é anterior á de início!");
        }
        this.fim = date;
    }
}
