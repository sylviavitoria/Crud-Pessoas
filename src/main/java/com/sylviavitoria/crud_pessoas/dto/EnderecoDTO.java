package com.sylviavitoria.crud_pessoas.dto;

import com.sylviavitoria.crud_pessoas.model.Endereco;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de um endereço")
public class EnderecoDTO {
    @Schema(description = "Identificador único do endereço", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Schema(description = "Nome da rua ou logradouro", example = "Avenida Paulista", required = true)
    private String rua;
    
    @Schema(description = "Número do endereço", example = "1000", required = true)
    private Integer numero;
    
    @Schema(description = "Bairro", example = "Bela Vista", required = true)
    private String bairro;
    
    @Schema(description = "Cidade", example = "São Paulo", required = true)
    private String cidade;
    
    @Schema(description = "Estado (sigla UF)", example = "SP", required = true)
    private String estado;
    
    @Schema(description = "CEP no formato 00000-000", example = "01310-100", required = true)
    private String cep;
    
    public EnderecoDTO() {
    }
    
    public EnderecoDTO(Endereco entity) {
        this.id = entity.getId();
        this.rua = entity.getRua();
        this.numero = entity.getNumero();
        this.bairro = entity.getBairro();
        this.cidade = entity.getCidade();
        this.estado = entity.getEstado();
        this.cep = entity.getCep();
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Endereco toEntity() {
        Endereco endereco = new Endereco();
        endereco.setId(this.id);
        endereco.setRua(this.rua);
        endereco.setNumero(this.numero);
        endereco.setBairro(this.bairro);
        endereco.setCidade(this.cidade);
        endereco.setEstado(this.estado);
        endereco.setCep(this.cep);
        return endereco;
    }
}