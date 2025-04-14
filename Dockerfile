# Etapa 1: Construção da Aplicação
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar o pom.xml e baixar dependências para cache eficiente
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar o código-fonte e os recursos
COPY src ./src

# Construir o projeto
RUN mvn clean package -DskipTests

# Etapa 2: Imagem Final
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copiar o JAR gerado e os recursos
COPY --from=build /app/target/*.jar .
COPY --from=build /app/src/main/resources /app/resources

# Definir o ponto de entrada com suporte a perfis dinâmicos
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=${ENVIRONMENT} $(ls *.jar)"]