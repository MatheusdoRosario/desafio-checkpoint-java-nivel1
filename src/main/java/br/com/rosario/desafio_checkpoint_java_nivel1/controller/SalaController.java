package br.com.rosario.desafio_checkpoint_java_nivel1.controller;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.SalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sala")
public class SalaController {

    @Autowired
    private SalaService service;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listar() {
        List<SalaDTO> salas = service.listar();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SalaDTO>> buscarPorID(@PathVariable Long id) {
        try {
            Optional<SalaDTO> sala = service.buscarPorID(id);
            return ResponseEntity.ok(sala);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrarSala(@RequestBody @Valid CadastroSalaDTO dto) {
        try {
            service.cadastrarSala(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarSala(@RequestBody @Valid AtualizacaoSalaDTO dto) {
        try {
            service.atualizarSala(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<String> ativarSala(@PathVariable Long id) {
        try {
            service.ativarSala(id);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<String> desativarSala(@PathVariable Long id) {
        try {
            service.desativarSala(id);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarSala(@PathVariable Long id) {
        try {
            service.deletarSala(id);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
