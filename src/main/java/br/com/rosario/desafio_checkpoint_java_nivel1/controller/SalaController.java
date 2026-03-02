package br.com.rosario.desafio_checkpoint_java_nivel1.controller;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.SalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/sala")
public class SalaController {

    @Autowired
    private SalaService service;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listar() {
        List<SalaDTO> salas = service.listar();
        return ResponseEntity.ok(salas);
    }

    public ResponseEntity<Optional<SalaDTO>> buscarPorID(Long id) {
        try {
            Optional<SalaDTO> sala = service.buscarPorID(id);
            return ResponseEntity.ok(sala);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<String> cadastrarSala(CadastroSalaDTO dto) {
        try {
            service.cadastrarSala(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> atualizarSala(AtualizacaoSalaDTO dto) {
        try {
            service.atualizarSala(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> ativarSala(Long id) {
        try {
            service.ativarSala(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> desativarSala(Long id) {
        try {
            service.desativarSala(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> deletarSala(Long id) {
        try {
            service.deletarSala(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
