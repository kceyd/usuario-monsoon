package com.example.usuariogg;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.usuariogg.assembler.UsuarioGGAssembler;
import com.example.usuariogg.controller.controladorusuario;
import com.example.usuariogg.model.Usuario;
import com.example.usuariogg.security.JWTFILTRO;
import com.example.usuariogg.security.JWTUTIL;
import com.example.usuariogg.service.serviciousuario;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controladorusuario.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(UsuarioGGAssembler.class)
public class ControladorUsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private serviciousuario usuarioService;

    @MockBean
    private JWTUTIL jwtutil;

    @MockBean
    private JWTFILTRO jwtfiltro;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testObtenerUsuarioPorId_DeberiaRetornarUsuarioConHateoas() throws Exception {
        Usuario usuarioMock = new Usuario(1L, "Juan Perez", "juan@duoc.cl", "juanitoGG", "1234", "USER");

        when(usuarioService.obtenerUsuario(1L)).thenReturn(usuarioMock);

        mockMvc.perform(get("/api/v0/usuarios/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.nombreUsuario").value("juanitoGG"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.usuarios.href").exists());
    }

    @Test
    public void testCrearUsuario_DeberiaRetornarStatusCreado() throws Exception {
        Usuario nuevoUsuario = new Usuario(null, "Diego GG", "diego@duoc.cl", "diegogg", "pass123", "USER");
        Usuario usuarioGuardado = new Usuario(2L, "Diego GG", "diego@duoc.cl", "diegogg", "pass123", "USER");

        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuarioGuardado);

        mockMvc.perform(post("/api/v0/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombreUsuario").value("diegogg"));
    }
}