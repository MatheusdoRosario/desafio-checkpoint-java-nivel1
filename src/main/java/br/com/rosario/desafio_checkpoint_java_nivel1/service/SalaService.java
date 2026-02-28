package br.com.rosario.desafio_checkpoint_java_nivel1.service;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.SalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    public List<SalaDTO> listar() {
        return repository
                .findAll()
                .stream()
                .map(SalaDTO::new)
                .toList();
    }

    public Optional<SalaDTO> buscarPorID(Long id) {
        return repository.findById(id)
                .map(SalaDTO::new);
    }

    public void criarSala(CadastroSalaDTO dto) {
        repository.save(new Sala(dto));
    }

    public void atualizarSala(AtualizacaoSalaDTO dto) {
        Sala sala = repository.getReferenceById(dto.id());
        sala.atualizarDados(dto);
    }

    public void ativarSala(Long id) {
        Sala sala = repository.getReferenceById(id);
        sala.ativarSala();
    }

    public void desativarSala(Long id) {
        Sala sala = repository.getReferenceById(id);
        sala.desativarSala();
    }

    public void deletarSala(Long id) {
        repository.deleteById(id);
    }
}
