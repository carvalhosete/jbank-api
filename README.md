# JBank API

API RESTful para gestão de contas bancárias e transferências financeiras, desenvolvida com foco em alta coesão e baixo acoplamento.

## Tecnologias Utilizadas

* Java 21
* Spring Boot 4.0.2
* Spring Data JPA
* Spring Doc (OpenAPI/Swagger)
* RabbitMQ (Mensageria)
* JUnit 5 & Mockito (Testes Unitários)
* Docker & Docker Compose

## Arquitetura

O projeto utiliza **Arquitetura Hexagonal (Ports and Adapters)** para garantir que as regras de negócio sejam independentes de frameworks e infraestrutura externa.

* **Core:** Contém o domínio e os casos de uso.
* **Adapters In:** Implementações de entrada (REST Controllers e Message Listeners).
* **Adapters Out:** Implementações de saída (Persistência e Integrações Externas).

## Como Executar o Projeto

### Pré-requisitos
* Docker Desktop instalado.
* JDK 21.

### Passo 1: Subir a infraestrutura
Na raiz do projeto, execute o comando para subir o RabbitMQ:
docker compose up -d

### Passo 2: Executar a aplicação
Pela sua IDE ou via terminal:
./mvnw spring-boot:run

### Passo 3: Documentação e Testes
A documentação interativa da API (Swagger) estará disponível em:
http://localhost:8080/swagger-ui/index.html

## Qualidade de Código
O projeto conta com validações rigorosas de entrada de dados (Bean Validation) e cobertura de testes unitários no core da aplicação, garantindo a integridade das regras de negócio.