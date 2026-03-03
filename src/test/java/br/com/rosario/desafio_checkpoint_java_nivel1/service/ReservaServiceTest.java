package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Reserva;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.StatusSala;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.ReservaRepository;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.SalaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @InjectMocks
    private ReservaService service;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private Reserva reserva;

    @Mock
    private Sala sala;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<Reserva> reservaPage;

    @Mock
    private CadastroReservaDTO cadastroReservaDTO;

    @Mock
    private AtualizaReservaDTO atualizaReservaDTO;

    @Test
    void deveRetornarReservaPorIdSemErro() {
        when(reservaRepository.findById(any(UUID.class))).thenReturn(Optional.of(reserva));

        service.buscarPorId(UUID.randomUUID());

        then(reservaRepository).should().findById(any(UUID.class));
    }

    @Test
    void naoDeveRetornarReservaPorIdComErro() {
        when(reservaRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.buscarPorId(UUID.randomUUID()));
    }

    @Test
    void deveRetornarReservasPorSalaSemErro() {
        when(reservaRepository.findBySalaId(1L, pageable)).thenReturn(reservaPage);

        service.buscarReservasPorSala(pageable, 1L);

        then(reservaRepository).should().findBySalaId(1L, pageable);
    }

    @Test
    void deveRetornarReservasPorUsuarioSemErro() {
        when(reservaRepository.findByUsuarioId(1L, pageable)).thenReturn(reservaPage);

        service.buscarReservasPorUsuario(pageable, 1L);

        then(reservaRepository).should().findByUsuarioId(1L, pageable);
    }

    @Test
    void deveCadastrarReservaSemErro() {
        when(cadastroReservaDTO.sala()).thenReturn(sala);
        when(sala.getId()).thenReturn(1L);
        when(sala.getCapacidade()).thenReturn(10);

        when(salaRepository.existsByIdAndStatusSala(1L, StatusSala.INATIVA)).thenReturn(false);
        when(reservaRepository.countBySalaId(1L)).thenReturn(5L);

        service.cadastrarReserva(cadastroReservaDTO);

        then(reservaRepository).should().save(any(Reserva.class));
    }

    @Test
    void naoDeveCadastrarReservaEmSalaInativa() {
        when(cadastroReservaDTO.sala()).thenReturn(sala);
        when(sala.getId()).thenReturn(1L);
        when(salaRepository.existsByIdAndStatusSala(1L, StatusSala.INATIVA)).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrarReserva(cadastroReservaDTO));
    }

    @Test
    void naoDeveCadastrarReservaEmSalaComLimiteDeCapacidade() {
        when(cadastroReservaDTO.sala()).thenReturn(sala);
        when(sala.getId()).thenReturn(1L);
        when(sala.getCapacidade()).thenReturn(10);

        when(salaRepository.existsByIdAndStatusSala(1L, StatusSala.INATIVA)).thenReturn(false);
        when(reservaRepository.countBySalaId(1L)).thenReturn(10L);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrarReserva(cadastroReservaDTO));
    }

    @Test
    void deveAtualizarReservaSemErro() {
        when(atualizaReservaDTO.sala()).thenReturn(sala);
        when(sala.getId()).thenReturn(1L);
        when(sala.getCapacidade()).thenReturn(10);

        when(salaRepository.existsByIdAndStatusSala(1L, StatusSala.INATIVA)).thenReturn(false);
        when(reservaRepository.countBySalaId(1L)).thenReturn(5L);
        when(reservaRepository.findById(atualizaReservaDTO.id())).thenReturn(Optional.of(reserva));

        service.atualizarReserva(atualizaReservaDTO);

        then(reservaRepository).should().findById(atualizaReservaDTO.id());
    }

    @Test
    void naoDeveAtualizarReservaEmSalaInativa() {
        when(atualizaReservaDTO.sala()).thenReturn(sala);
        when(sala.getId()).thenReturn(1L);

        when(salaRepository.existsByIdAndStatusSala(1L, StatusSala.INATIVA)).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarReserva(atualizaReservaDTO));
    }

    @Test
    void naoDeveAtualizarReservaEmSalaComLimiteDeCapacidade() {
        when(atualizaReservaDTO.sala()).thenReturn(sala);
        when(sala.getId()).thenReturn(1L);
        when(sala.getCapacidade()).thenReturn(10);

        when(salaRepository.existsByIdAndStatusSala(1L, StatusSala.INATIVA)).thenReturn(false);
        when(reservaRepository.countBySalaId(1L)).thenReturn(10L);

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarReserva(atualizaReservaDTO));
    }

    @Test
    void deveCancelarReservaSemErro() {
        when(reservaRepository.findById(any(UUID.class))).thenReturn(Optional.of(reserva));

        service.cancelarReserva(UUID.randomUUID());

        then(reservaRepository).should().findById(any(UUID.class));
    }

    @Test
    void naoDeveCancelarReservaInexistente() {

        when(reservaRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.cancelarReserva(UUID.randomUUID()));
    }

    @Test
    void deveAdicionarFimDaReservaSemErro() {
        LocalDate inicio = LocalDate.of(2026, 3, 1);
        LocalDate fim = LocalDate.of(2026, 3, 5);
        when(reservaRepository.findById(any(UUID.class))).thenReturn(Optional.of(reserva));
        when(reserva.getInicio()).thenReturn(inicio);

        service.adicionarDataFimDaReserva(UUID.randomUUID(), fim);

        then(reservaRepository).should().findById(any(UUID.class));
        then(reserva).should().adicionarFimDaReserva(fim);
    }

    @Test
    void naoDeveAdicionarFimDaReservaEmReservaInexistente() {
        when(reservaRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.adicionarDataFimDaReserva(UUID.randomUUID(), LocalDate.now()));
    }

    @Test
    void naoDeveAdicionarFimDaReservaComFimPosteriorAoInicio() {
        LocalDate inicio = LocalDate.of(2026, 3, 5);
        LocalDate fim = LocalDate.of(2026, 3, 1);
        when(reservaRepository.findById(any(UUID.class))).thenReturn(Optional.of(reserva));
        when(reserva.getInicio()).thenReturn(inicio);

        Assertions.assertThrows(ValidacaoException.class, () -> service.adicionarDataFimDaReserva(UUID.randomUUID(), fim));
    }

}