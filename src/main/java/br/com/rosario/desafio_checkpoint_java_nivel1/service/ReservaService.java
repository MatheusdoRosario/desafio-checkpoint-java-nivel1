package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.ReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Reserva;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.StatusSala;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.ReservaRepository;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private SalaRepository salaRepository;

    public ReservaDTO buscarPorId(UUID id) {
        return repository
                .findById(id)
                .map(ReservaDTO::new)
                .orElseThrow(() -> new ValidacaoException("Reserva não encontrada!"));
    }

    public List<ReservaDTO> buscarReservasPorSala(Long id) {
        return repository
                .findBySalaId(id)
                .stream()
                .map(ReservaDTO::new)
                .toList();
    }

    public List<ReservaDTO> buscarReservasPorUsuario(Long id) {
        return repository
                .findByUsuarioId(id)
                .stream()
                .map(ReservaDTO::new)
                .toList();
    }

    public void cadastrarReserva(CadastroReservaDTO dto) {
        if (salaRepository.existsByIdAndStatusSala(dto.sala().getId(), StatusSala.INATIVA)) {
            throw new ValidacaoException("Sala inativa!");
        }
        long reservas = repository.countBySalaId(dto.sala().getId());
        if (reservas >= dto.sala().getCapacidade()) {
            throw new ValidacaoException("Sala com limite de capacidade atingida!");
        }
        repository.save(new Reserva(dto));
    }

    public void atualizarReserva(AtualizacaoReservaDTO dto) {
        if (salaRepository.existsByIdAndStatusSala(dto.sala().getId(), StatusSala.INATIVA)) {
            throw new ValidacaoException("Sala inativa!");
        }
        long reservas = repository.countBySalaId(dto.sala().getId());
        if (reservas >= dto.sala().getCapacidade()) {
            throw new ValidacaoException("Sala com limite de capacidade atingida!");
        }

        Reserva reserva = repository.findById(dto.id())
                        .orElseThrow(() -> new ValidacaoException("Reserva não encontrada!"));
        reserva.atualizarDados(dto);
    }

    public void cancelarReserva(UUID id) {
        Reserva reserva = repository.findById(id)
                        .orElseThrow(() -> new ValidacaoException("Reserva não encontrada!"));
        reserva.cancelarReserva(reserva);
    }

    public void adicionarDataFimDaReserva(UUID id, LocalDate date) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Reserva não encontrada!"));
        if (reserva.getInicio().isAfter(date)){
            throw new ValidacaoException("A data de fim é anterior á de início!");
        }
        reserva.adicionarFimDaReserva(date);
    }
}
