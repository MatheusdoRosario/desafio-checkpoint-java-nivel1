package br.com.rosario.desafio_checkpoint_java_nivel1.controller;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizacaoReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.ReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reserva")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable UUID id) {
        try {
            ReservaDTO reserva = service.buscarPorId(id);
            return ResponseEntity.ok(reserva);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sala/{id}")
    public ResponseEntity<Page<ReservaDTO>> buscarReservasPorSala(Pageable pageable, @PathVariable Long id) {
        Page<ReservaDTO> reservas = service.buscarReservasPorSala(pageable, id);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Page<ReservaDTO>> buscarReservasPorUsuario(Pageable pageable, @PathVariable Long id) {
        Page<ReservaDTO> reservas = service.buscarReservasPorUsuario(pageable, id);
        return ResponseEntity.ok(reservas);
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
    public ResponseEntity<String> adicionarDataFimDaReserva(@PathVariable UUID id, @RequestBody LocalDate date) {
        try {
            service.adicionarDataFimDaReserva(id, date);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
