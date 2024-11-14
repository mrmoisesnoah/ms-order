# MS Order - Microserviço para Gestão de Pedidos

## Sobre o Projeto

Este é um microserviço desenvolvido para gestão de pedidos, utilizando o **Spring Framework**. Ele faz integração com **RabbitMQ** para comunicação assíncrona, **JPA** para persistência de dados e **Swagger** para documentação da API. O sistema é modular e fácil de testar, com endpoints bem definidos e documentação disponível via Swagger. E conta com cobertura de testes unitarios com Mockito.

### OBSERVAÇÃO: Infelizmente enfretei dificuldades dado aos procedimentos de segurança para instalação de softwares utilizando o computador corporativo(dado que estou sem computador pessoal). Aos que irão avaliar, informo que precisei utilizar o H2 devido ao prazo de entrega, e as dificuldades que enfrentei para instação e configuração do ambiente de desenvolvimento - isso inclui instalação do MySQL ou PostgreSQL, que foram os bancos que considerava mais apropriado aos parametros do teste.  

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para a construção da aplicação.
- **Spring Data JPA**: Para persistência de dados no banco de dados.
- **Spring AMQP**: Para integração com RabbitMQ.
- **Swagger**: Para documentação da API.
- **H2 Database**: (NÃO FOI POSSIVEL FAZER A INSTALAÇÃO DOS BANCOS QUE CONSIDEREI MAIS CORRETOS PARA ESSA SOLUÇÃO) Banco de dados em memória para desenvolvimento.
- **JUnit & Mockito**: Para testes unitários.
- **RabbitMQ**: Para comunicação assíncrona entre os serviços.
- **Java 17**: Versão do JDK utilizada.

## Como Utilizar a API

### Endpoints Disponíveis

A aplicação oferece diversos endpoints para gerenciar os pedidos. A documentação completa dos endpoints pode ser acessada via Swagger após rodar a aplicação, no seguinte link:

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

### Payload para `OrderDTO`

Aqui está um exemplo de payload para um objeto `OrderDTO` que pode ser enviado para a fila `orders.details-requests`:

```json
{
    "ordersId": 324,
    "customerId": 321,
    "dateTime": "2024-11-12T16:45:00",
    "status": "PLACED",
    "items": [
        {
            "productId": 1,
            "quantity": 2,
            "description": "Product A",
            "price": 10.99
        },
        {
            "productId": 2,
            "quantity": 1,
            "description": "Product B",
            "price": 25.50
        }
    ]
}


```

### Campos do `OrderDTO`:

- `ordersId` (Long): Identificador único do pedido.
- `customerId` (Long): ID do cliente que fez o pedido.
- `dateTime` (String - ISO 8601): Data e hora em que o pedido foi feito.
- `status` (String): Status atual do pedido. Exemplos de valores: 
  - `PLACED`: Pedido foi realizado com sucesso, mas ainda não processado.
  - `CANCELED`: Pedido foi cancelado.
  - `PAID`: Pagamento foi realizado com sucesso.
  - `NOT_AUTHORIZED`: O pagamento não foi autorizado.
  - `CONFIRMED`: Pedido foi confirmado para processamento.
  - `READY`: Pedido está pronto para ser enviado.
  - `OUT_FOR_DELIVERY`: Pedido está em processo de entrega.
  - `DELIVERED`: Pedido foi entregue ao cliente.
- `totalPrice` (Double): Valor total do pedido, calculado com base no preço e quantidade dos itens.
- `items` (Array): Lista de itens do pedido. Cada item deve conter:
  - `productId` (Long): Identificador do item.
  - `quantity` (Integer): Quantidade do item.
  - `description` (String): Descrição do produto.
  - `price` (Double): Preço unitário do produto.


## Como Rodar a Aplicação com Docker

### Passo 1: Instalando o Docker

1. **Instalar o Docker**:

   - Para **Windows** e **Mac**: Baixe e instale o Docker Desktop [aqui](https://www.docker.com/products/docker-desktop).
   - Para **Linux**: Siga as instruções específicas para a sua distribuição [aqui](https://docs.docker.com/engine/install/).

2. **Verificar a instalação do Docker**:

   Após instalar, verifique se o Docker está instalado corretamente executando o comando:

   ```bash
   docker --version
   ```

### Passo 2: Rodando o RabbitMQ com Docker

Para rodar o RabbitMQ com Docker, você pode usar o seguinte comando:

```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

#### Explicação do comando:

- `docker run`: Inicia um novo container.
- `-it`: Executa o container de forma interativa.
- `--rm`: Remove o container automaticamente quando ele for parado.
- `--name rabbitmq`: Dá o nome `rabbitmq` ao container.
- `-p 5672:5672`: Mapeia a porta 5672 (default do RabbitMQ) do container para a porta 5672 da máquina host.
- `-p 15672:15672`: Mapeia a porta 15672 (porta do painel de administração do RabbitMQ) do container para a porta 15672 da máquina host.
- `rabbitmq:3.9-management`: Especifica a imagem do RabbitMQ com o plugin de gerenciamento.

Após rodar o comando, o RabbitMQ estará acessível nas seguintes URLs:
- **RabbitMQ (porta 5672)**: Para conexão com a fila.
- **Painel de Administração (porta 15672)**: [http://localhost:15672](http://localhost:15672) (usuário: `guest`, senha: `guest`).

### Passo 3: Enviando o Payload para a Fila do RabbitMQ

1. **Acesse o Painel de Administração**:

   Abra o navegador e acesse o painel de administração do RabbitMQ em [http://localhost:15672](http://localhost:15672) com as credenciais padrão:
   - **Usuário**: `guest`
   - **Senha**: `guest`

2. **Criar uma Fila (se necessário)**:

   - Vá até a aba "Queues" no painel.
   - Clique em "Add a new queue" e crie uma fila com o nome `orders.details-requests` (caso não tenha sido criada automaticamente).

3. **Publicar uma Mensagem na Fila**:

   - No painel, vá até a fila `orders.details-requests` e clique em "Publish Message".
   - Cole o payload JSON (como mostrado acima) na área de mensagem e clique em "Publish".

   O serviço `OrderListener` estará ouvindo essa fila e processará a mensagem assim que for recebida.

### Como Rodar a Aplicação Localmente

1. **Clone o repositório para sua máquina local**:

   ```bash
   git clone https://github.com/seu-usuario/ms-order.git
   cd ms-order
   ```

2. **Compile o projeto com Maven**:

   ```bash
   mvn clean install
   ```

3. **Execute a aplicação com o Maven**:

   ```bash
   mvn spring-boot:run
   ```

   A aplicação será iniciada na porta `8080` (ou em uma porta aleatória se configurado com `server.port=0`).

4. **Acesse a documentação Swagger**:

   Acesse os endpoints da aplicação através da documentação Swagger no seguinte link:

   [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)
