# 🧪Desafio CRUD - Pessoas e endereço

 Este projeto Java com Spring Boot tem como objetivo implementar um CRUD completo para as entidades Pessoa e Endereço, que possuem um relacionamento um-para-muitos (uma pessoa pode ter vários endereços).

## 🔁 Relacionamento

- **Uma Pessoa pode ter vários Endereços**
- Ao excluir uma Pessoa, todos os seus Endereços devem ser excluídos em cascata

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


## 🛠 Como Executar

1. Clone o repositório:
   ```bash
      git clone https://github.com/sylviavitoria/Crud-Pessoas.git
   
 2. Execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acesse o H2 Console (opcional):
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
