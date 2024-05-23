# Sistema de Gerenciamento de Projetos

## Descrição
Este projeto é uma API RESTful desenvolvida com Spring Boot e MySQL para gerenciamento de projetos, tarefas, usuários, notificações, impedimentos e comentários. A API oferece funcionalidades básicas de CRUD (Criar, Ler, Atualizar, Excluir) para cada recurso, além de autenticação e autorização de usuários.

## Funcionalidades Principais

### Autenticação e Autorização
- **Registro de novos usuários:** `POST /auth/register`
- **Login de usuários com geração de token JWT:** `POST /auth/login`
- **Proteção de endpoints com autenticação baseada em token**
- **Controle de acesso baseado em roles (tipos de usuários)**

### Gerenciamento de Usuários
- **Listagem de todos os usuários (sem senha):** `GET /users`
- **Busca de usuário por email:** `GET /users/{email}`
- **Atualização de dados do usuário (nome, email, tipo, senha):** `PUT /users/{id}`
- **Exclusão de usuário por email:** `DELETE /users/{email}`

### Gerenciamento de Projetos
- **CRUD de projetos**
- **Listagem de projetos de um usuário:** `GET /users/{userId}/project`

### Gerenciamento de Tarefas
- **CRUD de tarefas**
- **Listagem de tarefas de um usuário:** `GET /userId/{userId}/task`
- **Atribuição de tarefas a usuários**
- **Gerenciamento do status da tarefa (pendente, em andamento, concluída, etc.)**

### Notificações
- **CRUD de notificações**
- **Listagem de notificações de um usuário:** `GET /userId/{userId}/notifications`

### Impedimentos
- **CRUD de impedimentos**
- **Listagem de impedimentos reportados por um usuário:** `GET /userId/{usuarioId}/impediments`


## Tecnologias Utilizadas

### Backend
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- JWT (JSON Web Tokens)
- Lombok

## Como Executar o Projeto

### Pré-requisitos
- Java JDK 17 ou superior
- Maven
- MySQL Server

### Configurar o Banco de Dados
1. Crie um banco de dados MySQL com o nome que você definiu em `application.properties`.
2. Crie as tabelas do banco de dados (você pode usar o script SQL fornecido em `resources/data.sql`).

### Clonar o Repositório
Clone este repositório para sua máquina local:
```bash
git clone https://github.com/seu-usuario/seu-projeto.git
