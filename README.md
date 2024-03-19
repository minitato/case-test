# case-test

## API de Transferências Bancárias

Este projeto é uma API de transferências bancárias desenvolvida para atender aos requisitos do desafio de engenharia de software. Abaixo está a descrição da arquitetura proposta para a implantação desta API na AWS:

## Arquitetura Proposta

![Diagrama de Solução na AWS com Spring Boot](URL_DA_IMAGEM)

**Explicação dos Componentes:**

1. **Amazon API Gateway:**
   - Recebe todas as solicitações da API dos clientes e encaminha para os serviços apropriados.

2. **Amazon RDS (Relational Database Service):**
   - Armazena os dados da aplicação. Escolhido um banco de dados relacional gerenciado pela AWS para suportar as necessidades de consistência e transações.

3. **Amazon ElastiCache:**
   - Cache distribuído para armazenar dados frequentemente acessados, reduzindo a latência das consultas ao banco de dados.

4. **Amazon CloudWatch:**
   - Utilizado para observabilidade. CloudWatch monitora métricas de desempenho, logs e eventos da aplicação.

5. **AWS Auto Scaling:**
   - Escala automaticamente os recursos de computação (Spring Boot, por exemplo) com base na carga de solicitações.

6. **Spring Boot (EC2 ou ECS):**
   - Implementa a lógica de negócios da API. Spring Boot é uma estrutura Java popular para desenvolvimento de aplicativos web. Pode ser implantado em instâncias EC2 ou em contêineres gerenciados ECS.

**Justificativas:**

1. **Escalonamento para Oscilação de Carga:**
   - A utilização de AWS Auto Scaling com instâncias EC2 ou contêineres ECS permite escalabilidade automática do Spring Boot com base na carga de solicitações.

2. **Proposta de Observabilidade:**
   - Amazon CloudWatch monitora métricas de desempenho, logs e eventos da aplicação para fornecer visibilidade completa.

3. **Escolha da Solução de Banco de Dados:**
   - Amazon RDS é escolhido devido à sua facilidade de uso e gerenciamento, além de oferecer suporte a bancos de dados relacionais como MySQL, PostgreSQL, entre outros.

4. **Justificativa do Uso de Caching:**
   - Amazon ElastiCache é utilizado para caching de dados frequentemente acessados, o que reduz a latência das consultas ao banco de dados e ajuda a manter o tempo de resposta abaixo de 100ms.

5. **Garantia de Tempo de Resposta Inferior a 100ms:**
   - A combinação de Spring Boot, Amazon RDS e Amazon ElastiCache é projetada para garantir tempos de resposta rápidos.

6. **Suporte a Alto Throughput (6 mil TPS):**
   - Spring Boot, Amazon RDS e Amazon ElastiCache são serviços altamente escaláveis que podem lidar com um alto volume de transações.

7. **Estratégia para Falhas de Dependência:**
   - O uso de Amazon SQS como uma fila de mensagens permite reprocessamento de mensagens em caso de falhas de dependência. Além disso, a capacidade de escalar automaticamente recursos ajuda a lidar com falhas de forma resiliente.


## Este repositório contém três projetos relacionados:

1. [Registration](registration/README.md): Um serviço de registro de clientes.
2. [Central Bank](central-bank-mock/README.md): Um serviço mock do Banco Central.
3. [Account](account/README.md): Um serviço de conta para transferências.

## Instruções de Configuração e Execução

Antes de executar qualquer um dos projetos, consulte as instruções de configuração e execução específicas de cada projeto:

- [Instruções do Registration](registration/README.md)
- [Instruções do Central Bank](central-bank-mock/README.md)
- [Instruções do Account](account/README.md)

Certifique-se de configurar e executar os serviços de registro e mock do Banco Central conforme necessário antes de executar o serviço de conta para transferências.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas (issues) ou enviar pull requests com melhorias.