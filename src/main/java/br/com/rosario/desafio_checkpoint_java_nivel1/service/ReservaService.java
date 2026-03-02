package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.ReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Reserva;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public Optional<ReservaDTO> buscarPorId(UUID id) {
        return repository
                .findById(id)
                .map(ReservaDTO::new);
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
        repository.save(new Reserva(dto));
    }

    public void atualizarReserva(AtualizacaoReservaDTO dto) {
        Reserva reserva = repository.getReferenceById(dto.id());
        reserva.atualizarDados(dto);
    }

    public void cancelarReserva(UUID id) {
        Reserva reserva = repository.getReferenceById(id);
        reserva.cancelarReserva();
    }

    public void adicionarDataFimDaReserva(UUID id, LocalDate date) {
        Reserva reserva = repository.getReferenceById(id);
        if (reserva.getInicio().isAfter(date)){
            throw new DateTimeException("A data de fim é anterior á de início!");
        }
        reserva.adicionarFimDaReserva(date);
    }
}
