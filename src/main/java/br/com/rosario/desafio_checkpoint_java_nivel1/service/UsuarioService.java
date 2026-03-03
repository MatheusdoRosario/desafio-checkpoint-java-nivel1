package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.UsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Page<UsuarioDTO> listar(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(UsuarioDTO::new);
    }

    public UsuarioDTO buscarPorId(Long id) {
            return repository
                    .findById(id)
                    .map(UsuarioDTO::new)
                    .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));
    }

    @Transactional
    public void cadastrarUsuario(CadastroUsuarioDTO dto) {
        if (repository.existsByTelefone(dto.telefone())) {
            throw new ValidacaoException("Telefone já cadastrado!");
        }
        repository.save(new Usuario(dto));
    }

    @Transactional
    public void atualizarUsuario(AtualizaUsuarioDTO dto) {
        if (repository.existsByTelefone(dto.telefone())) {
            throw new ValidacaoException("Telefone já cadastrado!");
        }
        Usuario usuario = repository.findById(dto.id())
                        .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));
        usuario.atualizarDados(dto);
    }

    @Transactional
    public void excluirUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new ValidacaoException("Usuário não encontrado!");
        }
        repository.deleteById(id);
    }
}