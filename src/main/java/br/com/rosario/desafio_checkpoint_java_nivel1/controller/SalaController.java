package br.com.rosario.desafio_checkpoint_java_nivel1.controller;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.SalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sala")
public class SalaController {

    @Autowired
    private SalaService service;

    @GetMapping
    public ResponseEntity<Page<SalaDTO>> listar(Pageable pageable) {
        Page<SalaDTO> salas = service.listar(pageable);
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarPorID(@PathVariable Long id) {
        try {
            SalaDTO sala = service.buscarPorID(id);
            return ResponseEntity.ok(sala);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrarSala(@RequestBody @Valid CadastroSalaDTO dto) {
        try {
            service.cadastrarSala(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarSala(@RequestBody @Valid AtualizaSalaDTO dto) {
        try {
            service.atualizarSala(dto);
            return ResponseEntity.noContent().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<String> ativarSala(@PathVariable Long id) {
        try {
            service.ativarSala(id);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/desativar")
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
            service.excluirSala(id);
            return ResponseEntity.noContent().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
