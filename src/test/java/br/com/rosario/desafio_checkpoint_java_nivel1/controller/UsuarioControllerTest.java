package br.com.rosario.desafio_checkpoint_java_nivel1.controller;

import br.com.rosario.desafio_checkpoint_java_nivel1.dto.AtualizaUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.dto.CadastroUsuarioDTO;
import br.com.rosario.desafio_checkpoint_java_nivel1.exception.ValidacaoException;
import br.com.rosario.desafio_checkpoint_java_nivel1.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioService service;

    @Autowired
    private JacksonTester<CadastroUsuarioDTO> cadastroJson;

    @Autowired
    private JacksonTester<AtualizaUsuarioDTO> atualizaJson;

    @Test
    void deveRetornar200AoListarUsuarios() throws Exception {
        var response = mvc.perform(
                get("/api/v1/usuario")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200AoBuscarUsuarioPorIdSemErro() throws Exception {
        var response = mvc.perform(
                get("/api/v1/usuario/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar404AoBuscarUsuarioPorIdInexistente() throws Exception {
        when(service.buscarPorId(1L)).thenThrow(new ValidacaoException("Usuário não encontrado!"));

        var response = mvc.perform(
                get("/api/v1/usuario/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveRetornar201AoCadastrarUsuarioSemErro() throws Exception {
        CadastroUsuarioDTO cadastroUsuarioDTO = new CadastroUsuarioDTO("Matheus", "22555554444");

        var response = mvc.perform(
                post("/api/v1/usuario")
                        .content(cadastroJson.write(cadastroUsuarioDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornar400AoCadastrarUsuarioComJsonInvalido() throws Exception {
        String json = "{}";
        doThrow(new ValidacaoException("Erro ao cadastrar!"))
                .when(service).cadastrarUsuario(any(CadastroUsuarioDTO.class));

        var response = mvc.perform(
                post("/api/v1/usuario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar201AoAtualizarUsuarioSemErro() throws Exception {
        AtualizaUsuarioDTO atualizaUsuarioDTO = new AtualizaUsuarioDTO(1L, "Matheus", "22555554444");

        var response = mvc.perform(
                put("/api/v1/usuario")
                        .content(atualizaJson.write(atualizaUsuarioDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void deveRetornar400AoAtualizarUsuarioComJsonInvalido() throws Exception {
        String json = "{}";
        doThrow(new ValidacaoException("Erro ao Atualizar!"))
                .when(service).atualizarUsuario(any(AtualizaUsuarioDTO.class));

        var response = mvc.perform(
                put("/api/v1/usuario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar204AoExcluirUsuarioSemErro() throws Exception {
        var response = mvc.perform(
                delete("/api/v1/usuario/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void deveRetornar404AoExcluirUsuarioInexistente() throws Exception {
        doThrow(new ValidacaoException("Usuario não encontrado!"))
                .when(service).excluirUsuario(1L);

        var response = mvc.perform(
                delete("/api/v1/usuario/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

}