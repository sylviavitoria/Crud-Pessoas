package com.sylviavitoria.crud_pessoas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sylviavitoria.crud_pessoas.config.SwaggerExamples.AtualizarPessoaExample;
import com.sylviavitoria.crud_pessoas.config.SwaggerExamples.CriarPessoaExample;
import com.sylviavitoria.crud_pessoas.dto.PessoaDTO;
import com.sylviavitoria.crud_pessoas.service.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pessoas")
@Tag(name = "Pessoas", description = "API para gerenciamento de pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    @Operation(
        summary = "Criar uma nova pessoa", 
        description = "Cria um registro de pessoa com seus dados pessoais e endereços associados"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou CPF já cadastrado")
    })
    public ResponseEntity<PessoaDTO> salvar(
        @Valid @CriarPessoaExample @RequestBody PessoaDTO pessoaDTO
    ) {
        PessoaDTO savedPessoa = pessoaService.salvar(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPessoa);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar pessoa", 
        description = "Atualiza os dados de uma pessoa existente a partir do seu ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou CPF já cadastrado para outra pessoa"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PessoaDTO> atualizar(
        @PathVariable Long id,
        @Valid @AtualizarPessoaExample @RequestBody PessoaDTO pessoaDTO
    ) {
        try {
            PessoaDTO updatedPessoa = pessoaService.atualizar(id, pessoaDTO);
            return ResponseEntity.ok(updatedPessoa);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(
        summary = "Listar todas as pessoas", 
        description = "Retorna uma lista com todas as pessoas cadastradas e seus endereços"
    )
    @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso")
    public ResponseEntity<List<PessoaDTO>> listarTodas() {
        List<PessoaDTO> pessoas = pessoaService.listarTodas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar pessoa por ID", 
        description = "Retorna os dados de uma pessoa específica a partir do seu ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long id) {
        try {
            PessoaDTO pessoa = pessoaService.buscarPorId(id);
            return ResponseEntity.ok(pessoa);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir pessoa", 
        description = "Remove uma pessoa e seus endereços associados do sistema"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            pessoaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}