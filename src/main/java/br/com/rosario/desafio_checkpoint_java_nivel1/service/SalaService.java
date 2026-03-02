package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.SalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.StatusSala;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    public Page<SalaDTO> listar(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(SalaDTO::new);
    }

    public SalaDTO buscarPorID(Long id) {
        return repository.findById(id)
                .map(SalaDTO::new)
                .orElseThrow(() -> new ValidacaoException("Sala não encontrada!"));
    }

    public void cadastrarSala(CadastroSalaDTO dto) {
        if (repository.existsByName(dto.nome())) {
            throw new ValidacaoException("Nome já cadastrado!");
        }
        repository.save(new Sala(dto));
    }

    public void atualizarSala(AtualizacaoSalaDTO dto) {
        if (repository.existsByNameAndIdNot(dto.nome(), dto.id())) {
            throw new ValidacaoException("Nome já cadastrado!");
        }
        Sala sala = repository.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Sala não encontrada!"));
        sala.atualizarDados(dto);
    }

    public void ativarSala(Long id) {
        if (repository.existsByIdAndStatusSala(id, StatusSala.DISPONIVEL)) {
            throw new ValidacaoException("Sala já está ativada!");
        }
        Sala sala = repository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Sala não encontrada!"));
        sala.ativarSala();
    }

    public void desativarSala(Long id) {
        if (repository.existsByIdAndStatusSala(id, StatusSala.INATIVA)) {
            throw new ValidacaoException("Sala já está desativada!");
        }
        Sala sala = repository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Sala não encontrada!"));
        sala.desativarSala();
    }

    public void deletarSala(Long id) {
        if (!repository.existsById(id)) {
            throw new ValidacaoException("Sala não encontrada!");
        }
        repository.deleteById(id);

    }
}
