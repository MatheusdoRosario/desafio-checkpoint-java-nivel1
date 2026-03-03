package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private Pageable pageable;

    @Mock
    private Usuario usuario;

    @Mock
    private CadastroUsuarioDTO cadastroUsuarioDTO;

    @Mock
    private AtualizaUsuarioDTO atualizaUsuarioDTO;

    @Test
    void deveListarUsuariosSemErro() {
        when(repository.findAll(pageable)).thenReturn(Page.empty());

        service.listar(pageable);

        then(repository).should().findAll(pageable);
    }

    @Test
    void deveRetornarUsuarioPorIdSemErro() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        service.buscarPorId(1L);

        then(repository).should().findById(1L);
    }

    @Test
    void naoDeveListarUsuarioPorIdInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.buscarPorId(1L));
    }

    @Test
    void deveCadastrarUsuarioSemErro() {
        when(repository.existsByTelefone(cadastroUsuarioDTO.telefone())).thenReturn(false);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        service.cadastrarUsuario(cadastroUsuarioDTO);

        then(repository).should().save(any(Usuario.class));
    }

    @Test
    void naoDeveCadastrarUsuarioComTelefoneCadastrado() {
        when(repository.existsByTelefone(cadastroUsuarioDTO.telefone())).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrarUsuario(cadastroUsuarioDTO));
    }

    @Test
    void deveAtualizarUsuarioSemErro() {
        when(repository.existsByTelefone(atualizaUsuarioDTO.telefone())).thenReturn(false);
        when(repository.findById(atualizaUsuarioDTO.id())).thenReturn(Optional.of(usuario));

        service.atualizarUsuario(atualizaUsuarioDTO);

        then(repository).should().findById(atualizaUsuarioDTO.id());
    }

    @Test
    void naoDeveAtualizarUsuarioComTelefoneCadastrado() {
        when(repository.existsByTelefone(atualizaUsuarioDTO.telefone())).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarUsuario(atualizaUsuarioDTO));
    }

    @Test
    void naoDeveAtualizarUsuarioInexistente() {
        when(repository.existsByTelefone(atualizaUsuarioDTO.telefone())).thenReturn(false);
        when(repository.findById(atualizaUsuarioDTO.id())).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarUsuario(atualizaUsuarioDTO));
    }

    @Test
    void deveExcluirUsuarioSemErro() {
        when(repository.existsById(1L)).thenReturn(true);

        service.excluirUsuario(1L);

        then(repository).should().deleteById(1L);
    }

    @Test
    void naoDeveExcluirUsuarioInexistente() {
        when(repository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(ValidacaoException.class, () -> service.excluirUsuario(1L));
    }

}