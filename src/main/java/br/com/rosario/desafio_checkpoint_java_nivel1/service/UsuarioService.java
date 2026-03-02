package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.UsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<UsuarioDTO> listar() {
            return repository
                    .findAll()
                    .stream()
                    .map(UsuarioDTO::new)
                    .toList();
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        try {
            return repository
                    .findById(id)
                    .map(UsuarioDTO::new);
        } catch (ValidacaoException e) {
            throw new ValidacaoException("Usuário não encontrado!");
        }
    }

    public void cadastrarUsuario(CadastroUsuarioDTO dto) {
        if (repository.existsByTelefone(dto.telefone())){
            throw new ValidacaoException("Telefone já cadastrado!");
        }
        repository.save(new Usuario(dto));
    }

    public void atualizarUsuario(AtualizacaoUsuarioDTO dto) {
        if (repository.existsByTelefone(dto.telefone())){
            throw new ValidacaoException("Telefone já cadastrado!");
        }
        Usuario usuario = repository.getReferenceById(dto.id());
        usuario.atualizarDados(dto);
    }

    public void excluirUsuario(Long id) {
        try {
            repository.deleteById(id);
        } catch (ValidacaoException e) {
            throw new ValidacaoException("Usuário não encontrado!");
        }
    }
}