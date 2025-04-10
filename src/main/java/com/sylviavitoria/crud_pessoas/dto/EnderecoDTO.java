package com.sylviavitoria.crud_pessoas.dto;

import com.sylviavitoria.crud_pessoas.model.Endereco;

public class EnderecoDTO {
    private Long id;
    private String rua;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;
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