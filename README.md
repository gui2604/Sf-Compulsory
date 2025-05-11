http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs

# SFCompulsory

## Swagger/OpenAPI:
### http://localhost:8080/swagger-ui.html
### http://localhost:8080/v3/api-docs

## 💡 Sobre o Projeto

SFCompulsory é uma API RESTful desenvolvida em Java com Spring Boot que tem como objetivo ajudar clientes a manter o controle sobre seus aportes financeiros. Por meio de um sistema de limites de investimento, os usuários podem estabelecer restrições que os auxiliam a evitar decisões compulsivas e descontroladas ao investir.

O projeto fornece funcionalidades básicas de autenticação basic, CRUD de usuários e gerenciamento de limites de aporte.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Security
  - Spring Data JPA
- **Maven** (Gerenciamento de dependências)
- **H2 Database** (banco em memória para testes)
- **JWT (JSON Web Token)** para autenticação
- **Lombok** (para reduzir boilerplate code)
- **JPA/Hibernate** (persistência de dados)

---

## 📁 Estrutura do Projeto

```bash
SFCompulsory/
├── src/main/java/
│   └── br/com/fiap/api/
│       ├── ApiApplication.java
│       ├── config/                # Configurações de segurança
│       ├── controller/            # Controladores REST (User, Auth, Healthcheck)
│       ├── dto/                   # Objetos de transferência de dados (Login, Reset de senha)
│       ├── model/                 # Entidades JPA
│       ├── repository/            # Interfaces de acesso ao banco
│       ├── service/               # Regras de negócio
│       └── teste/main/           # Possíveis testes manuais
├── src/main/resources/
│   └── application.properties    # Configurações da aplicação
├── src/test/java/
│   └── br/com/fiap/api/          # Testes automatizados
├── Dockerfile                    # Containerização da aplicação
├── docker_instructions.txt       # Instruções para execução via Docker
├── query_admin_user.sql          # Script de criação de usuário admin
├── pom.xml                       # Gerenciador de dependências Maven
└── README.md

# Clone o repositório
git clone https://github.com/seu-usuario/SFCompulsory.git
cd SFCompulsory

# Compile e execute
./mvnw spring-boot:run

# Testes:
./mvnw test

# Docker
## A aplicação está disponibilizada em um container publico:
docker pull gui2604/sf-compulsory:v1.0.1
docker run --name container-sf-compulsory -p 8080:8080 gui2604/sf-compulsory:v1.0.1


