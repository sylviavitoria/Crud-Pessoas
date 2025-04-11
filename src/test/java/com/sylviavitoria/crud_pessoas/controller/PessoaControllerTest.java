package com.sylviavitoria.crud_pessoas.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sylviavitoria.crud_pessoas.controllers.PessoaController;
import com.sylviavitoria.crud_pessoas.dto.PessoaDTO;
import com.sylviavitoria.crud_pessoas.service.PessoaService;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PessoaService pessoaService;

    private ObjectMapper objectMapper;
    private PessoaDTO pessoaDTO;
    private List<PessoaDTO> pessoas;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("João Silva");
        pessoaDTO.setCpf("12345678900");
        pessoaDTO.setDataNascimento(LocalDate.of(1990, 1, 1));
        
        pessoas = Arrays.asList(pessoaDTO);
    }

    @Test
    public void testListarTodas() throws Exception {
        when(pessoaService.listarTodas()).thenReturn(pessoas);

        mockMvc.perform(get("/api/pessoas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuscarPorId_Sucesso() throws Exception {
        when(pessoaService.buscarPorId(1L)).thenReturn(pessoaDTO);

        mockMvc.perform(get("/api/pessoas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuscarPorId_NaoEncontrado() throws Exception {
        when(pessoaService.buscarPorId(99L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/api/pessoas/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSalvar() throws Exception {
        PessoaDTO novaPessoa = new PessoaDTO();
        novaPessoa.setNome("Maria Oliveira");
        novaPessoa.setCpf("98765432100");
        novaPessoa.setDataNascimento(LocalDate.of(1995, 5, 15));

        when(pessoaService.salvar(any(PessoaDTO.class))).thenReturn(novaPessoa);

        mockMvc.perform(post("/api/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novaPessoa)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAtualizar_Sucesso() throws Exception {
        PessoaDTO pessoaAtualizada = new PessoaDTO();
        pessoaAtualizada.setId(1L);
        pessoaAtualizada.setNome("João Silva Atualizado");
        
        when(pessoaService.atualizar(eq(1L), any(PessoaDTO.class))).thenReturn(pessoaAtualizada);

        mockMvc.perform(put("/api/pessoas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaAtualizada)))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testAtualizar_NaoEncontrado() throws Exception {
        PessoaDTO pessoaAtualizada = new PessoaDTO();
        pessoaAtualizada.setId(99L);
        pessoaAtualizada.setNome("Pessoa Inexistente");
        pessoaAtualizada.setCpf("99999999999");
        
        when(pessoaService.atualizar(eq(99L), any(PessoaDTO.class))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/api/pessoas/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaAtualizada)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testAtualizar_ArgumentoInvalido() throws Exception {
        PessoaDTO pessoaAtualizada = new PessoaDTO();
        pessoaAtualizada.setId(1L);
        pessoaAtualizada.setNome("João Silva Atualizado");
        pessoaAtualizada.setCpf("12345678900");
        
        when(pessoaService.atualizar(eq(1L), any(PessoaDTO.class))).thenThrow(new IllegalArgumentException("CPF já cadastrado"));

        mockMvc.perform(put("/api/pessoas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaAtualizada)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testExcluir_Sucesso() throws Exception {
        doNothing().when(pessoaService).excluir(1L);

        mockMvc.perform(delete("/api/pessoas/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    public void testExcluir_NaoEncontrado() throws Exception {
        doThrow(new EntityNotFoundException()).when(pessoaService).excluir(99L);

        mockMvc.perform(delete("/api/pessoas/99"))
                .andExpect(status().isNotFound());
    }
}