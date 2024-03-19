# Documentação de Referência

## Conta para Transferências com Resilience4j Circuit Breaker

Este projeto é uma implementação de um serviço de Conta para fazer transferências usando Spring Boot e o Resilience4j Circuit Breaker para lidar com falhas e atrasos no serviço do Banco Central.

## Visão Geral

O objetivo deste projeto é fornecer uma aplicação de exemplo que simula o serviço de uma conta bancária para transferências de fundos. O serviço interage com um serviço de notificação do Banco Central mock. O Resilience4j Circuit Breaker é utilizado para proteger a aplicação contra falhas e lentidão no serviço externo.

## Configuração

## Pré-requisitos

Antes de executar o serviço de Conta para Transferências, você precisará configurar e executar os seguintes serviços:

- **Registro de Clientes (registration)**: Um serviço responsável pelo registro de clientes. [Instruções de Configuração e Execução](link_para_documentação_do_registro_de_clientes).

- **Banco Central Mock (central-bank-mock)**: Um serviço mock que simula o serviço do Banco Central. Ele é usado para notificações e retornos de status, como HTTP 429 (Too Many Requests). [Instruções de Configuração e Execução](link_para_documentação_do_banco_central_mock).

### Requisitos

- Java Development Kit (JDK) 11 ou superior
- Maven

### Instalação e Execução

1. Clone este repositório:

   ```bash
   https://github.com/minitato/case-test.git
   ```

2. Navegue até o diretório do projeto:

    ```bash
    cd case-test/account
    ```
3. Compile o projeto com Gradle

    ```bash
    ./gradlew clean build
    ```
4. Execute o serviço Spring Boot:
    ```bash
    java -jar build/libs/account-0.0.1-SNAPSHOT.jar
    ```

## Uso
O serviço estará disponível em http://localhost:8080. Ele possui os seguintes endpoints:

GET /accounts/transfer/{sourceName}/{money}/{targetName}: Para fazer uma transferência entre contas.
Exemplo de solicitação cURL para fazer uma transferência:

1. Exemplo de solicitação cURL:
    ```bash
    curl --location 'http://localhost:8080/accounts/transfer/John/100/Jane'
    ```

    ou

    Em repeitição:

    ```bash
    ./script/requests_get_test.sh origem destino 100
    ```

    Substitua origem, destino e 100 pelos valores desejados para sourceName, targetName e money respectivamente. O script então usará esses parâmetros na URL de destino para a solicitação GET.

    Obs: lista clientes deveria em testes apenas esse tabela foi cadastrado do clientes (registation), veja abaixo:

        | ID  | Nome  |
        | --- | ----- |
        | 1   | John  |
        | 2   | Jane  |


## Resilience4j Circuit Breaker
O Resilience4j Circuit Breaker é configurado para proteger o serviço contra falhas no serviço do Banco Central. Se o serviço do Banco Central mock retornar o status HTTP 429 (Too Many Requests), o circuito será aberto e um retorno de fallback será fornecido.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas (issues) ou enviar pull requests com melhorias.