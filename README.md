# MS Order - Microservice for Order Management

## Sobre o projeto

Este projeto é um microserviço responsável pela gestão de pedidos. Ele foi desenvolvido utilizando o **Spring Framework** e possui integração com **RabbitMQ** para comunicação assíncrona, **JPA** para persistência de dados e **Swagger** para documentação dos endpoints.

A aplicação foi projetada para ser altamente modular e fácil de testar. Além disso, a aplicação também oferece documentação dos endpoints via Swagger.

## Como utilizar a API

### Endpoints Disponíveis

A aplicação oferece diversos endpoints para gerenciar os pedidos. Para acessar a documentação completa dos endpoints, você pode usar o Swagger, que está disponível após rodar a aplicação, no seguinte link:

[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

### **Endpoints de Pedido (OrderController)**

#### 1. **Listagem de Pedidos (Paginação)**

- **Método**: `GET`
- **Endpoint**: `/orders`
- **Descrição**: Retorna uma lista de pedidos paginada.
- **Parâmetros**:
  - `page` (opcional): Página para a busca paginada.
  - `size` (opcional): Quantidade de itens por página.

**Exemplo de requisição**:

```http
GET http://localhost:8080/orders?page=0&size=10
```

#### 2. **Consultar Pedido por ID**

- **Método**: `GET`
- **Endpoint**: `/orders/{id}`
- **Descrição**: Retorna um pedido específico pelo ID.
  
**Exemplo de requisição**:

```http
GET http://localhost:8080/orders/1
```

### **Processamento de Pedidos (OrderListener)**

A aplicação também possui integração com **RabbitMQ**, onde a fila de pedidos é ouvida pelo serviço para processar os pedidos de forma assíncrona.

- **Fila**: `orders.details-requests`
- **Descrição**: Quando um pedido é colocado na fila `orders.details-requests`, ele será processado pelo `OrderListener`, que irá criar ou atualizar o pedido com base no ID do pedido.

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para construção da aplicação.
- **Spring Data JPA**: Para persistência de dados no banco de dados.
- **Spring AMQP**: Para integração com RabbitMQ.
- **Swagger**: Para documentação da API.
- **H2 Database**: Banco de dados em memória para desenvolvimento.
- **JUnit & Mockito**: Para testes unitários.
- **Eureka Client**: Para descoberta de serviços (caso utilizado com outros microsserviços).
- **Java 17**: Versão do JDK utilizada.
- **RabbitMQ**: Para comunicação assíncrona de pedidos.

## Como Rodar a Aplicação

1. Clone o repositório para sua máquina local.

   ```bash
   git clone https://github.com/seu-usuario/ms-order.git
   cd ms-order
   ```

2. Compile o projeto utilizando o Maven.

   ```bash
   mvn clean install
   ```

3. Execute a aplicação com o comando:

   ```bash
   mvn spring-boot:run
   ```

   O microserviço será iniciado na porta `8080` ou em uma porta aleatória (se configurado com `server.port=0`).

4. Acesse a aplicação via Swagger no seguinte link:

   [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

