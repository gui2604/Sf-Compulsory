# SFCompulsory

## 🚀 3ESPV - Engenharia de Software 3º Ano
### 🧑‍💻 Guilherme Barreto Santos - RM97674
### 🧑‍💻 Henrique Parra - RM551973
### 🧑‍💻 Nicolas Oliveira da Silva - RM98939
### 🧑‍💻 Roberto Oliveira - RM551460
### 🧑‍💻 Tony Willian - RM550667

# SFCompulsory

## 📌 Visão Geral

SFCompulsory é uma API RESTful desenvolvida em **Java 17** com **Spring Boot**, criada para ajudar clientes a manter controle sobre seus aportes financeiros.  

O sistema permite que os usuários estabeleçam **limites de investimento**, evitando decisões impulsivas ou descontroladas. A API fornece funcionalidades de:  

- Autenticação de usuários (com **Spring Security** e **JWT**).  
- CRUD completo de usuários.  
- Reset de senha seguro.  
- Registro de logs de atividades.  

A aplicação também possui **Swagger/OpenAPI** integrado para documentação interativa.  

---

## 🌐 Endpoints de Swagger / OpenAPI

- Interface interativa: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
- Documentação em JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)  

---

## 🛠 Tecnologias Utilizadas

- **Java 17**  
- **Spring Boot 3.4**  
  - Spring Web  
  - Spring Security  
  - Spring Data JPA / Hibernate  
- **Flyway** (para controle de migrations)  
- **H2 Database** (banco em memória para testes)  
- **JWT (JSON Web Token)** para autenticação  
- **Lombok** (para redução de boilerplate code)  
- **Maven** (gerenciamento de dependências)  
- **Postman** (testes da API)  

---

## 🗂 Estrutura do Projeto

```bash
SFCompulsory/
├── src/main/java/
│   └── br/com/fiap/api/
│       ├── ApiApplication.java        # Classe principal
│       ├── config/                    # Configurações de segurança e JWT
│       ├── controller/                # Controladores REST (User, Auth, Healthcheck)
│       ├── dto/                       # DTOs para requests (UserCreateDTO, UserUpdateDTO, ResetPasswordRequest, etc)
│       ├── model/                     # Entidades JPA (User, Log)
│       ├── repository/                # Interfaces de persistência (UserRepository, LogRepository)
│       ├── service/                   # Regras de negócio (UserService, LogSummaryService)
│       └── vo/                        # Value Objects (UsernameVO, EmailVO, PasswordVO)
├── src/main/resources/
│   ├── db/migration/                  # Scripts de migrations Flyway
│   │   ├── V1__create_users_table.sql
│   │   ├── V2__create_logs_table.sql
│   │   └── V3__query_admin_user.sql
│   └── application.properties         # Configurações da aplicação
├── src/test/java/
│   └── br/com/fiap/api/               # Testes automatizados
├── Dockerfile                          # Containerização da aplicação
├── docker_instructions.txt             # Instruções de execução via Docker
├── pom.xml                             # Gerenciador de dependências Maven
└── README.md
```
---

## 💾 Migrations

A API utiliza **Flyway** para versionamento e criação de tabelas:

1. **V1__create_users_table.sql** → Cria a tabela `users`.  
2. **V2__create_logs_table.sql** → Cria a tabela `logs`.  
3. **V3__query_admin_user.sql** → Insere usuário admin inicial.  

> 🔹 **Observação:** Sempre execute as migrations na ordem correta para evitar erros de validação do Flyway.

---

## 🔑 Rotas Disponíveis

### Usuários (`/api/v1/users`)

| Método | Endpoint | Descrição | Body (JSON) |
|--------|----------|-----------|-------------|
| GET    | `/`      | Lista todos os usuários | - |
| GET    | `/{id}`  | Busca usuário por ID | - |
| POST   | `/`      | Cria um novo usuário | `UserCreateDTO` |
| PUT    | `/{id}`  | Atualiza usuário (todos os campos) | `UserUpdateDTO` |
| PATCH  | `/{id}`  | Atualiza parcialmente o usuário | `UserUpdateDTO` |
| DELETE | `/{id}`  | Remove usuário | - |

### Autenticação

| Método | Endpoint | Descrição | Body (JSON) |
|--------|----------|-----------|-------------|
| POST   | `/api/v1/auth/login` | Autentica usuário e retorna JWT | `{ "username": "...", "password": "..." }` |
| POST   | `/api/v1/auth/reset-password` | Redefine senha do usuário | `ResetPasswordRequest` |

---

## 📦 DTOs e VOs

- **UserCreateDTO**: usado para criação de usuário. Contém **VOs** para validar campos como email, senha e username.  
- **UserUpdateDTO**: usado para atualização parcial ou completa.  
- **VOs** (`EmailVO`, `UsernameVO`, `PasswordVO`) garantem consistência e validação de dados.  

---

## 🔒 Segurança

- Autenticação com **JWT** e **Spring Security**.  
- Senhas armazenadas criptografadas com **BCrypt**.  
- Reset de senha requer senha atual e validação via DTO.  

---

## 🧪 Testes Unitários

- UserServiceTest → cobre métodos: createUser, updateUser, delete, autenticateUser, resetPassword.
- LogSummaryServiceTest → cobre métodos: addLog e getSummary.

- UserControllerTest → verifica endpoints REST de /api/v1/users, incluindo GET, POST, PUT, PATCH e DELETE.
- AuthControllerTest → verifica endpoints de autenticação e reset de senha (/login, /reset-password).
- LogControllerTest → verifica endpoint /api/logs/summary.

- Rodar Testes
```bash
./mvnw test

```

🔹 Observação: Testes utilizam H2 Database para simulação de repositório e Mockito para mocks de dependências como UserRepository e PasswordEncoder.

---

## 🧪 Testes com Postman

Exemplo de rotas configuradas em **Postman Collection**:

- **Listar usuários** → GET `/api/v1/users`  
- **Criar usuário** → POST `/api/v1/users`  

```json
{
  "clientName": "João Silva",
  "email": { "value": "joao@teste.com" },
  "username": { "value": "joaos" },
  "password": { "value": "123456" },
  "betMaxValue": 5000,
  "userPixKey": "joaos-pix"
}
```
Atualizar usuário → PUT /api/v1/users/100
```json
{
  "clientName": "João da Silva",
  "email": {"value": "joaodasilva@email.com"},
  "betMaxValue": 600,
  "userPixKey": "09876543211"
}
```

Resetar senha → POST /api/v1/auth/reset-password
```json
{
  "currentPassword": "senhaAtual",
  "newPassword": "novaSenha123"
}
```

> 🔹 ***💡 Dica:*** Use o usuário admin já criado na V3 da migration para autenticação inicial (username: admin, password: admin123).

> 🔹 ***🛢️ Collection:*** Pode baixar a collection do projeto por meio do arquivo: 'Challenge SFCompulsory.postman_collection.json' presente na raiz do projeto.

## 📊 Diagrama Entidade-Relacionamento
O diagrama entidade-relacionamento pode ser encontrado no arquivo 'diagrama_ER.png' no diretório raíz do projeto.

## 🛠 Build & Run
### Clonar o repositório
git clone https://github.com/gui2604/Sf-Compulsory
cd SFCompulsory

### Rodar a aplicação
./mvnw spring-boot:run

### Rodar testes
./mvnw test

## 🐳 Docker
### A aplicação está disponibilizada em um container publico:
docker pull gui2604/sf-compulsory:v1.1.0
docker run --name container-sf-compulsory -p 8080:8080 gui2604/sf-compulsory:v1.1.0