package br.com.rosario.desafio_checkpoint_java_nivel1.entity;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
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

    public Sala(CadastroSalaDTO dto) {
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
        this.reservas = 0;
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

    public void desativarSala() {
        this.statusSala = StatusSala.INATIVA;
    }

    public void ativarSala() {
        this.statusSala = StatusSala.DISPONIVEL;
    }

    public void reservar() {
        this.reservas += 1;
    }

    public void cancelarReserva() {
        this.reservas -= 1;
    }

    public void atualizarDados(AtualizacaoSalaDTO dto) {
        this.id = dto.id();
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
    }
}
