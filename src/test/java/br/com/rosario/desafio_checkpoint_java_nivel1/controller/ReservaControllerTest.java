package br.com.rosario.desafio_checkpoint_java_nivel1.controller;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.*;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Sala;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.StatusReserva;
import br.com.rosario.desafio_checkpoint_java_nivel1.entity.Usuario;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.service.ReservaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ReservaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservaService service;

    @Mock
    private ReservaDTO reservaDTO;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<ReservaDTO> reservaDTOPage;

    @Autowired
    private JacksonTester<CadastroReservaDTO> cadastroJson;

    @Autowired
    private JacksonTester<AtualizacaoReservaDTO> atualizacaoJson;

    @Test
    void deveRetornarCodigo200AoBuscarReservasPorIdSemErro() throws Exception {
        when(service.buscarPorId(any(UUID.class))).thenReturn(reservaDTO);

        var response = mvc.perform(get("/api/v1/reserva/" + UUID.randomUUID()))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400AoBuscarReservasPorIdComErro() throws Exception {
        when(service.buscarPorId(any(UUID.class))).thenThrow(new ValidacaoException("Reserva não encontrada"));

        var response = mvc.perform(get("/api/v1/reserva/" + UUID.randomUUID()))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());

    }

    @Test
    void deveRetornarCodigo200AoBuscarReservasPorSala() throws Exception {
        when(service.buscarReservasPorSala(pageable, 1L)).thenReturn(reservaDTOPage);

        var response = mvc.perform(get("/api/v1/reserva/sala/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200AoBuscarReservasPorUsuario() throws Exception {
        when(service.buscarReservasPorUsuario(pageable, 1L)).thenReturn(reservaDTOPage);

        var response = mvc.perform(get("/api/v1/reserva/usuario/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200AoCadastrarReservaSemErro() throws Exception {
        CadastroReservaDTO cadastroReservaDTO = new CadastroReservaDTO(new Sala(new CadastroSalaDTO("Sala 1", 10)),
                new Usuario(new CadastroUsuarioDTO("Matheus", "22555554444")));

        var response = mvc.perform(post("/api/v1/reserva")
                        .content(cadastroJson.write(cadastroReservaDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400AoCadastrarReservaComErro() throws Exception {
        String json = "{}";
        doThrow(new ValidacaoException("Erro ao atualizar"))
                .when(service).cadastrarReserva(any(CadastroReservaDTO.class));

        var response = mvc.perform(post("/api/v1/reserva")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200AoAtualizarReservaSemErro() throws Exception {
        AtualizacaoReservaDTO atualizacaoReservaDTO = new AtualizacaoReservaDTO(UUID.randomUUID(),
                LocalDate.now(),
                new Sala(new CadastroSalaDTO("Sala 1", 10)),
                new Usuario(new CadastroUsuarioDTO("Matheus", "22555554444")),
                StatusReserva.ATIVA);

        var response = mvc.perform(put("/api/v1/reserva")
                        .content(atualizacaoJson.write(atualizacaoReservaDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400AoAtualizarReservaComErro() throws Exception {
        String json = "{}";
        doThrow(new ValidacaoException("Erro ao atualizar"))
                .when(service).atualizarReserva(any(AtualizacaoReservaDTO.class));

        var response = mvc.perform(put("/api/v1/reserva")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200AoCancelarReservaSemErro() throws Exception {
        var response = mvc.perform(put("/api/v1/reserva/cancelar/" + UUID.randomUUID()))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo404AoCancelarReservaComErro() throws Exception {
        doThrow(new ValidacaoException("Erro ao cancelar"))
                .when(service).cancelarReserva(any(UUID.class));

        var response = mvc.perform(put("/api/v1/reserva/cancelar/" + UUID.randomUUID()))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveRetornarCodigo200AoAdicionarFimDaReservaSemErro() throws Exception {
        String json = """
                "2026-03-03"
                """;

        var response = mvc.perform(put("/api/v1/reserva/fim/" + UUID.randomUUID())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornarCodigo400AoAdicionarFimDaReservaComErro() throws Exception {
        String json = "{}";

        var response = mvc.perform(put("/api/v1/reserva/fim/" + UUID.randomUUID())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}