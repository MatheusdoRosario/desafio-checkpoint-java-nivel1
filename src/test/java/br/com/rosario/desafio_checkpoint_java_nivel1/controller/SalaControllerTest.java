package br.com.rosario.desafio_checkpoint_java_nivel1.controller;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaReservaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroSalaDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.service.SalaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class SalaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CadastroSalaDTO> cadastroJson;

    @Autowired
    private JacksonTester<AtualizaSalaDTO> atualizaJson;

    @MockBean
    private SalaService service;

    @Test
    void deveRetornar200AoListarSalas() throws Exception {
        var response = mvc.perform(
                get("/api/v1/sala")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200AoBuscarSalaPorIdSemErro() throws Exception {
        var response = mvc.perform(
                get("/api/v1/sala/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar404AoBuscarSalaPorIdComErro() throws Exception {
        when(service.buscarPorID(1L)).thenThrow(new ValidacaoException("Sala não encontrada"));

        var response = mvc.perform(
                get("/api/v1/sala/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveRetornar201AoCadastrarSalaSemErro() throws Exception {
        CadastroSalaDTO cadastroSalaDTO = new CadastroSalaDTO("Sala 1", 1);

        var response = mvc.perform(
                post("/api/v1/sala")
                        .content(cadastroJson.write(cadastroSalaDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornar400AoCadastrarSalaComErro() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                post("/api/v1/sala")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar204AoAtualizarSalaSemErro() throws Exception {
        AtualizaSalaDTO atualizaSalaDTO = new AtualizaSalaDTO( 1L, "Sala 1", 2);

        var response = mvc.perform(
                put("/api/v1/sala")
                        .content(atualizaJson.write(atualizaSalaDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void deveRetornar400AoAtualizarSalaComErro() throws Exception {
        String json = "{}";
        doThrow(new ValidacaoException("Erro ao atualizar"))
                .when(service).atualizarSala(any(AtualizaSalaDTO.class));

        var response = mvc.perform(
                put("/api/v1/sala")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar200AoAtivarSalaSemErro() throws Exception {
        var response = mvc.perform(
                put("/api/v1/sala/1/ativar")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar400AoAtivarSalaComErro() throws Exception {
        doThrow(new ValidacaoException("Sala já ativa!"))
                .when(service).ativarSala(1L);

        var response = mvc.perform(
                put("/api/v1/sala/1/ativar")
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar200AoDesativarSalaSemErro() throws Exception {
        var response = mvc.perform(
                put("/api/v1/sala/1/desativar")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar400AoDesativarSalaComErro() throws Exception {
        doThrow(new ValidacaoException("Sala já desativada!"))
                .when(service).desativarSala(1L);

        var response = mvc.perform(
                put("/api/v1/sala/1/desativar")
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar200AoExcluirSalaSemErro() throws Exception {
        var response = mvc.perform(
                delete("/api/v1/sala/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void deveRetornar404AoExcluirSalaComErro() throws Exception {
        doThrow(new ValidacaoException("Sala não encontrada!"))
                .when(service).deletarSala(1L);

        var response = mvc.perform(
                delete("/api/v1/sala/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }
}