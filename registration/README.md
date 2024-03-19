# Documentação de Referência - Serviço de Registro (Registration)

Este projeto é um serviço de registro de usuários implementado usando Spring Boot.

## Visão Geral

O serviço de registro é responsável por fornecer endpoints para consultar usuários e gerenciar suas informações no sistema.

## Tecnologias Utilizadas

- Spring Boot 2.x
- Spring Data JDBC
- Spring Web
- H2 Database (para ambiente de desenvolvimento)

## Configuração e Execução

### Requisitos

- Java Development Kit (JDK) 11 ou superior
- Gradle 8.6

### Passos para Execução

### Instalação e Execução

1. Clone este repositório:

   ```bash
   https://github.com/minitato/case-test.git
   ```

2. Navegue até o diretório do projeto:

    ```bash
    cd case-test/registration
    ```
3. Compile o projeto com Gradle

    ```bash
    ./gradlew clean build
    ```
4. Execute o serviço Spring Boot:
    ```bash
    java -jar build/libs/registration-0.0.1-SNAPSHOT.jar
    ```

## Uso
O serviço estará disponível em http://localhost:8081. Ele aceita solicitações GET para o endpoint /registration/customer/{name} para consultar ao Serviço do registros de clientes.

1. Exemplo de solicitação cURL:
    ```bash
    curl --location 'http://localhost:8081/registration/customer/John'
    ```

## Endpoints
Aqui estão os principais endpoints oferecidos pelo serviço:


GET /registration/customer/{nome}: Recupera as informações de um usuário específico.
Para mais detalhes sobre os endpoints e os formatos de dados esperados, consulte a documentação da API ou os comentários no código-fonte.

## Banco de Dados
O serviço utiliza um banco de dados H2 em memória para ambiente de desenvolvimento. Para ambientes de produção, você deve configurar um banco de dados adequado (como MySQL, PostgreSQL, etc.) e ajustar a configuração do Spring Boot conforme necessário.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas (issues) ou enviar pull requests com melhorias.

