# 🧪Desafio CRUD - Pessoas e endereço

 Este projeto Java com Spring Boot tem como objetivo implementar um CRUD completo para as entidades Pessoa e Endereço, que possuem um relacionamento um-para-muitos (uma pessoa pode ter vários endereços).

## 📋 Requisitos

- Criar uma nova pessoa com um ou mais endereços
- Listar todas as pessoas e seus respectivos endereços
- Atualizar dados da pessoa e/ou seus endereços
- Excluir uma pessoa (e automaticamente todos os seus endereços)
- Mostrar a **idade** da pessoa (calculada com base na data de nascimento)
- Banco de dados: **H2 em memória**
- Todas as respostas devem ser em **JSON**
- Implementar validações básicas e tratamento de exceções
- Aplicar boas práticas de REST e estruturação do projeto

---

## 🔁 Relacionamento

- **Uma Pessoa pode ter vários Endereços**
- Ao excluir uma Pessoa, todos os seus Endereços devem ser excluídos em cascata

Seguindo o anexo do diagrama:
![image](https://github.com/user-attachments/assets/fee564bb-f005-481c-8590-a499f52686d7)


## ✅ Funcionalidades

- [x] Listar todas as pessoas e seus respectivos endereços
- [x] Criar uma nova pessoa com um ou mais endereços
- [x] Atualizar os dados de uma pessoa e/ou seu(s) endereço(s)
- [x] Excluir uma pessoa e todos os seus endereços
- [x] Mostrar a idade da pessoa, calculada a partir da data de nascimento

## 🚀 Tecnologias Utilizadas

- Java 21+
- Spring Boot
- Spring Data JPA
- H2 Database (em memória)
- Lombok
- Maven
- JSON como formato de troca de dados
- Postman

---

## 🔗 Endpoints da API

### 🔍 Listar uma pessoa por ID
`GET /api/pessoas/{id}`  
`GET http://localhost:8080/api/pessoas/1`

---

### 📄 Listar todas as pessoas
`GET /api/pessoas`  
`GET http://localhost:8080/api/pessoas`

---

### ➕ Criar nova pessoa
`POST http://localhost:8080/api/pessoas`  
**Exemplo:**  
```json
{
  "nome": "Julia Almeida",
  "dataNascimento": "1995-08-25",
  "cpf": "12378945633",
  "enderecos": [
    {
      "rua": "Rua Augusta",
      "numero": 500,
      "bairro": "Consolação",
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "01304-000"
    },
    {
      "rua": "Av. Atlântica",
      "numero": 2000,
      "bairro": "Copacabana",
      "cidade": "Rio de Janeiro",
      "estado": "RJ",
      "cep": "22021-001"
    }
  ]
}
```

---

### ✏ Atualizar pessoa
`PUT http://localhost:8080/api/pessoas/{id}`  
**Exemplo:**  
```json
{
  "nome": "João Silva",
  "dataNascimento": "1990-05-15",
  "cpf": "123.456.789-99",
  "enderecos": [
    {
      "id": 1,
      "rua": "Rua das Flores Atualizada",
      "numero": 123,
      "bairro": "Centro",
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "01234-567"
    },
    {
      "rua": "Nova Rua",
      "numero": 789,
      "bairro": "Novo Bairro",
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "09876-543"
    }
  ]
}
```

---

### ❌ Excluir pessoa
`DELETE http://localhost:8080/api/pessoas/{id}`  
**Exemplo:**  
`DELETE http://localhost:8080/api/pessoas/2`

---

## 🛠 Como Executar

1. Clone o repositório:
   ```bash
      git clone https://github.com/sylviavitoria/Crud-Pessoas.git
   
 2. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

3. Acesse o H2 Console (opcional):
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
