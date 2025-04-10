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

}