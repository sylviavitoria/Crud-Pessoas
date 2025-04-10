package com.sylviavitoria.crud_pessoas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sylviavitoria.crud_pessoas.dto.PessoaDTO;
import com.sylviavitoria.crud_pessoas.service.PessoaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaDTO> salvar(@Valid @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO savedPessoa = pessoaService.salvar(pessoaDTO);
        return ResponseEntity.ok(savedPessoa);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodas() {
        List<PessoaDTO> pessoas = pessoaService.listarTodas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long id) {
        try {
            PessoaDTO pessoa = pessoaService.buscarPorId(id);
            return ResponseEntity.ok(pessoa);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PessoaDTO pessoaDTO) {
        try {
            PessoaDTO updatedPessoa = pessoaService.atualizar(id, pessoaDTO);
            return ResponseEntity.ok(updatedPessoa);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            pessoaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}