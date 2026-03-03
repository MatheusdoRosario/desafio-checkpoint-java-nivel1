package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.StatusSala;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.SalaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @InjectMocks
    private SalaService service;

    @Mock
    private SalaRepository repository;

    @Mock
    private Pageable pageable;

    @Mock
    private Sala sala;

    @Mock
    private CadastroSalaDTO cadastroSalaDTO;

    @Mock
    private AtualizaSalaDTO atualizaSalaDTO;

    @Test
    void deveListarSalas() {
        when(repository.findAll(pageable)).thenReturn(Page.empty());

        service.listar(pageable);

        then(repository).should().findAll(pageable);
    }

    @Test
    void deveListarSalaPorIdSemErro() {
        when(repository.findById(1L)).thenReturn(Optional.of(sala));

        service.buscarPorID(1L);

        then(repository).should().findById(1L);
    }

    @Test
    void naoDeveListarSalaPorIdComErro() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.buscarPorID(1L));
    }

    @Test
    void deveCadastrarSalaSemErro() {
        when(repository.existsByNome(cadastroSalaDTO.nome())).thenReturn(false);
        when(repository.save(any(Sala.class))).thenReturn(sala);

        service.cadastrarSala(cadastroSalaDTO);

        then(repository).should().save(any(Sala.class));
    }

    @Test
    void naoDeveCadastrarSalaComErro() {
        when(repository.existsByNome(cadastroSalaDTO.nome())).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrarSala(cadastroSalaDTO));
    }

    @Test
    void deveAtualizarSalaSemErro() {
        when(repository.existsByNomeAndIdNot(atualizaSalaDTO.nome(), atualizaSalaDTO.id())).thenReturn(false);
        when(repository.findById(atualizaSalaDTO.id())).thenReturn(Optional.of(sala));

        service.atualizarSala(atualizaSalaDTO);

        then(repository).should().findById(atualizaSalaDTO.id());
    }

    @Test
    void naoDeveAtualizarSalaComErro() {
        when(repository.existsByNomeAndIdNot(atualizaSalaDTO.nome(), atualizaSalaDTO.id())).thenReturn(false);
        when(repository.findById(atualizaSalaDTO.id())).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarSala(atualizaSalaDTO));
    }

    @Test
    void deveAtivarSalaSemErro() {
        when(repository.existsByIdAndStatusSala(1L, StatusSala.DISPONIVEL)).thenReturn(false);
        when(repository.findById(1L)).thenReturn(Optional.of(sala));

        service.ativarSala(1L);

        then(repository).should().findById(1L);
    }

    @Test
    void naoDeveAtivarSalaComErro() {
        when(repository.existsByIdAndStatusSala(1L, StatusSala.DISPONIVEL)).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.ativarSala(1L));
    }

    @Test
    void deveDesativarSalaSemErro() {
        when(repository.existsByIdAndStatusSala(1L, StatusSala.INATIVA)).thenReturn(false);
        when(repository.findById(1L)).thenReturn(Optional.of(sala));

        service.desativarSala(1L);

        then(repository).should().findById(1L);
    }

    @Test
    void naoDeveDesativarSalaComErro() {
        when(repository.existsByIdAndStatusSala(1L, StatusSala.INATIVA)).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.desativarSala(1L));
    }

    @Test
    void deveExcluirSalaSemErro() {
        when(repository.existsById(1L)).thenReturn(true);

        service.excluirSala(1L);

        then(repository).should().deleteById(1L);
    }

    @Test
    void naoDeveExcluirSalaComErro() {
        when(repository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(ValidacaoException.class, () -> service.excluirSala(1L));
    }

}