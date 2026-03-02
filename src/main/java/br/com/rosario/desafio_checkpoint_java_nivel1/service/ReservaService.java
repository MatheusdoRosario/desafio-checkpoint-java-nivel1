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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public Page<ReservaDTO> buscarReservasPorSala(Pageable pageable, Long id) {
        return repository
                .findBySalaId(id, pageable)
                .map(ReservaDTO::new);
    }

    public Page<ReservaDTO> buscarReservasPorUsuario(Pageable pageable, Long id) {
        return repository
                .findByUsuarioId(id, pageable)
                .map(ReservaDTO::new);
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public void cancelarReserva(UUID id) {
        Reserva reserva = repository.findById(id)
                        .orElseThrow(() -> new ValidacaoException("Reserva não encontrada!"));
        reserva.cancelarReserva(reserva);
    }

    @Transactional
    public void adicionarDataFimDaReserva(UUID id, LocalDate date) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Reserva não encontrada!"));
        if (reserva.getInicio().isAfter(date)){
            throw new ValidacaoException("A data de fim é anterior á de início!");
        }
        reserva.adicionarFimDaReserva(date);
    }
}
