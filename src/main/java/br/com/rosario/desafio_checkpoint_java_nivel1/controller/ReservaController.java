package br.com.rosario.desafio_checkpoint_java_nivel1.controller;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.ReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReservaDTO>> buscarPorId(@PathVariable UUID id) {
        try {
            Optional<ReservaDTO> reserva = service.buscarPorId(id);
            return ResponseEntity.ok(reserva);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sala/{id}")
    public ResponseEntity<List<ReservaDTO>> buscarReservasPorSala(@PathVariable Long id) {
        try {
            List<ReservaDTO> reservas = service.buscarReservasPorSala(id);
            return ResponseEntity.ok(reservas);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ReservaDTO>> buscarReservasPorUsuario(@PathVariable Long id) {
        try {
            List<ReservaDTO> reservas = service.buscarReservasPorUsuario(id);
            return ResponseEntity.ok(reservas);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrarReserva(@RequestBody @Valid CadastroReservaDTO dto) {
        try {
            service.cadastrarReserva(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarReserva(@RequestBody @Valid AtualizacaoReservaDTO dto) {
        try {
            service.atualizarReserva(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> cancelarReserva(@PathVariable UUID id) {
        try {
            service.cancelarReserva(id);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/fim/{id}")
    public ResponseEntity<String> adicionarDataFimDaReserva(@PathVariable UUID id,@RequestBody LocalDate date) {
        try {
            service.adicionarDataFimDaReserva(id, date);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
