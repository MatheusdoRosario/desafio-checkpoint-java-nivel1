package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.UsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
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
        return repository
                .findById(id)
                .map(UsuarioDTO::new);
    }

    public void cadastrarUsuario(CadastroUsuarioDTO dto) {
        repository.save(new Usuario(dto));
    }

    public void atualizarUsuario(AtualizacaoUsuarioDTO dto) {
        Usuario usuario = repository.getReferenceById(dto.id());
        usuario.atualizarDados(dto);
    }

    public void excluirUsuario(Long id) {
        repository.deleteById(id);
    }
}