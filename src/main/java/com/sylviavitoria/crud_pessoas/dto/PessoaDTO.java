package com.sylviavitoria.crud_pessoas.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sylviavitoria.crud_pessoas.model.Pessoa;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de uma pessoa")
public class PessoaDTO {
    @Schema(description = "Identificador único da pessoa", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Schema(description = "Nome completo da pessoa", example = "Joao da Silva", required = true)
    private String nome;
    
    @Schema(description = "Data de nascimento no formato ISO", example = "1990-01-01", required = true)
    private LocalDate dataNascimento;
    
    @Schema(description = "CPF da pessoa (somente números ou formatado)", example = "170.950.450-00", required = true)
    private String cpf;
    
    @Schema(description = "Lista de endereços associados à pessoa")
    private List<EnderecoDTO> enderecos = new ArrayList<>();
    
    @Schema(description = "Idade calculada a partir da data de nascimento", example = "00", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idade;
    

    public PessoaDTO() {
    }
    
    public PessoaDTO(Pessoa entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.idade = entity.getIdade();
        
        if (entity.getDataNascimento() != null && !entity.getDataNascimento().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                this.dataNascimento = LocalDate.parse(entity.getDataNascimento(), formatter);
            } catch (Exception e) {
                
            }
        }
        
        this.enderecos = entity.getEnderecos().stream()
                .map(endereco -> new EnderecoDTO(endereco))
                .collect(Collectors.toList());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Pessoa toEntity() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(this.id);
        pessoa.setNome(this.nome);
        pessoa.setCpf(this.cpf);
        
        if (this.dataNascimento != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            pessoa.setDataNascimento(this.dataNascimento.format(formatter));
        }
        
        return pessoa;
    }
}