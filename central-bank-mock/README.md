# Documentação de Referência 

## Notificação do Banco Central (Mock) com Spring Boot e Bucket4j

Este projeto é uma implementação de um serviço de notificação mock do Banco Central usando Spring Boot e limitação de taxa de pedidos com Bucket4j.

## Visão Geral

O objetivo deste projeto é fornecer uma simulação de serviço de notificação do Banco Central para integração com sistemas externos. A implementação utiliza o framework Spring Boot para criar o serviço web e o Bucket4j para limitar a taxa de pedidos.

## Configuração

### Requisitos

- Java Development Kit (JDK) 21 ou superior

### Instalação e Execução

1. Clone este repositório:

   ```bash
   https://github.com/minitato/case-test.git
   ```

2. Navegue até o diretório do projeto:

    ```bash
    cd case-test/central-bank-mock
    ```
3. Compile o projeto com Gradle

    ```bash
    ./gradlew clean build
    ```
4. Execute o serviço Spring Boot:
    ```bash
    java -jar build/libs/central-bank-mock-0.0.1-SNAPSHOT.jar
    ```

## Uso
O serviço estará disponível em http://localhost:9001. Ele aceita solicitações POST para o endpoint /notificacao para enviar notificações ao Banco Central mock.

1. Exemplo de solicitação cURL:
    ```bash
    curl --location 'http://localhost:9001/api/notification/transaction' \
    --header 'Content-Type: application/json' \
    --data '{
        "name": "Jane"
    }'
    ```
## Limitação de Taxa de Pedidos
Para evitar abusos, este serviço é configurado para aceitar no máximo 20 pedidos por minuto de um mesmo cliente. Se o limite for excedido, o serviço retornará um status HTTP 429 (Too Many Requests).

## Configuração Personalizada
Se necessário, o limite de taxa de pedidos pode ser ajustado no arquivo application.yml. Você pode alterar a propriedade bucket4j: limite (criar nova linha) para o valor desejado. Por padrão, o limite está configurado para 20 pedidos por minuto.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas (issues) ou enviar pull requests com melhorias.
