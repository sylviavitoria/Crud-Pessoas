package com.sylviavitoria.crud_pessoas.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sylviavitoria.crud_pessoas.dto.PessoaDTO;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

public class SwaggerExamples {

    /**
     * Exemplo para criar uma nova pessoa
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @RequestBody(
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PessoaDTO.class),
            examples = {
                @ExampleObject(
                    name = "novaPessoa",
                    summary = "Exemplo para criar nova pessoa",
                    value = "{\n" +
                           "  \"nome\": \"Maria Silva\",\n" +
                           "  \"dataNascimento\": \"1995-05-15\",\n" +
                           "  \"cpf\": \"123.456.789-00\",\n" +
                           "  \"enderecos\": [\n" +
                           "    {\n" +
                           "      \"rua\": \"Rua das Flores\",\n" +
                           "      \"numero\": 123,\n" +
                           "      \"bairro\": \"Jardim\",\n" +
                           "      \"cidade\": \"São Paulo\",\n" +
                           "      \"estado\": \"SP\",\n" +
                           "      \"cep\": \"01234-567\"\n" +
                           "    }\n" +
                           "  ]\n" +
                           "}"
                )
            }
        )
    )
    public @interface CriarPessoaExample {}
    
    /**
     * Exemplo para atualizar uma pessoa existente
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @RequestBody(
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PessoaDTO.class),
            examples = {
                @ExampleObject(
                    name = "atualizarPessoa",
                    summary = "Exemplo para atualizar pessoa existente",
                    value = "{\n" +
                           "  \"nome\": \"João Silva\",\n" +
                           "  \"dataNascimento\": \"1990-05-15\",\n" +
                           "  \"cpf\": \"123.456.789-99\",\n" +
                           "  \"enderecos\": [\n" +
                           "    {\n" +
                           "      \"id\": 1,\n" +
                           "      \"rua\": \"Rua das Flores Atualizada\",\n" +
                           "      \"numero\": 123,\n" +
                           "      \"bairro\": \"Centro\",\n" +
                           "      \"cidade\": \"São Paulo\",\n" +
                           "      \"estado\": \"SP\",\n" +
                           "      \"cep\": \"01234-567\"\n" +
                           "    },\n" +
                           "    {\n" +
                           "      \"rua\": \"Nova Rua\",\n" +
                           "      \"numero\": 789,\n" +
                           "      \"bairro\": \"Novo Bairro\",\n" +
                           "      \"cidade\": \"São Paulo\",\n" +
                           "      \"estado\": \"SP\",\n" +
                           "      \"cep\": \"09876-543\"\n" +
                           "    }\n" +
                           "  ]\n" +
                           "}"
                )
            }
        )
    )
    public @interface AtualizarPessoaExample {}
}