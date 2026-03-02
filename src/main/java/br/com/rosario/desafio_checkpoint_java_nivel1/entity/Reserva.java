package br.com.rosario.desafio_checkpoint_java_nivel1.entity;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroReservaDTO;
import jakarta.persistence.*;

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

    public Reserva(CadastroReservaDTO dto) {
        this.sala = dto.sala();
        this.usuario = dto.usuario();
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

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void cancelarReserva(Reserva reserva) {
        this.statusReserva = StatusReserva.CANCELADA;
        sala.cancelarReserva(reserva);
    }

    public void adicionarFimDaReserva(LocalDate date) {
        this.fim = date;
    }

    public void atualizarDados(AtualizacaoReservaDTO dto) {
        this.id = dto.id();
        this.sala = dto.sala();
        this.usuario = dto.usuario();
        this.statusReserva = dto.statusReserva();
    }
}
