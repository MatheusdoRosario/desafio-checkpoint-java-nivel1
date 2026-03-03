package br.com.rosario.desafio_checkpoint_java_nivel1.entity;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int capacidade;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    @Enumerated(EnumType.STRING)
    private StatusSala statusSala;

    public Sala() {
    }

    public Sala(CadastroSalaDTO dto) {
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
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

    public List<Reserva> getReservas() {
        return reservas;
    }

    public StatusSala getStatusSala() {
        return statusSala;
    }

    public void desativarSala() {
        this.statusSala = StatusSala.INATIVA;
    }

    public void ativarSala() {
        this.statusSala = StatusSala.DISPONIVEL;
    }

    public void reservar(Reserva reserva) {
        this.reservas.add(reserva);
    }

    public void cancelarReserva(Reserva reserva) {
        this.reservas.remove(reserva);
    }

    public void atualizarDados(AtualizaSalaDTO dto) {
        this.id = dto.id();
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
    }

    public void setNome(String salaDeReunião) {

    }
}
