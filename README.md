1
Sistema de Gerenciamento de Projetos
Descrição
Este projeto é uma API RESTful desenvolvida com Spring Boot e MySQL para gerenciamento de projetos, tarefas, usuários, notificações, impedimentos e comentários. A API oferece funcionalidades básicas de CRUD (Criar, Ler, Atualizar, Excluir) para cada recurso, além de autenticação e autorização de usuários.

Funcionalidades Principais
Autenticação e Autorização:
Registro de novos usuários (POST /auth/register).
Login de usuários com geração de token JWT (POST /auth/login).
Proteção de endpoints com autenticação baseada em token.
Controle de acesso baseado em roles (tipos de usuários).
Gerenciamento de Usuários:
Listagem de todos os usuários (sem senha) (GET /usuarios).
Busca de usuário por email (GET /usuarios/{email}).
Atualização de dados do usuário (nome, email, tipo, senha) (PUT /usuarios/{id}).
Exclusão de usuário por email (DELETE /usuarios/{email}).
Gerenciamento de Projetos:
Criação, leitura, atualização e exclusão de projetos.
Listagem de projetos de um usuário (GET /usuarios/{usuarioId}/projetos).
Gerenciamento de Tarefas:
Criação, leitura, atualização e exclusão de tarefas.
Listagem de tarefas de um usuário (GET /usuarios/{usuarioId}/tarefas).
Atribuição de tarefas a usuários.
Gerenciamento do status da tarefa (pendente, em andamento, concluída, etc.).
Notificações:
Criação, leitura e exclusão de notificações.
Listagem de notificações de um usuário (GET /usuarios/{usuarioId}/notificacoes).
Impedimentos:
Criação, leitura e exclusão de impedimentos.
Listagem de impedimentos reportados por um usuário (GET /usuarios/{usuarioId}/impedimentos).
Comentários:
Criação, leitura e exclusão de comentários em tarefas.
Listagem de comentários de um usuário (GET /usuarios/{usuarioId}/comentarios).
Tecnologias Utilizadas
Backend:
Spring Boot
Spring Data JPA
Spring Security
MySQL
JWT (JSON Web Tokens)
Lombok
Testes:
JUnit 5
Mockito
AssertJ
Spring Boot Test
H2 Database (para testes)
Como Executar o Projeto
Pré-requisitos:
Java JDK 17 ou superior
Maven
MySQL Server
Configurar o Banco de Dados:
Crie um banco de dados MySQL com o nome que você definiu em application.properties.
Crie as tabelas do banco de dados (você pode usar o script SQL fornecido anteriormente).
Clonar o Repositório:
Clone este repositório para sua máquina local: git clone https://github.com/seu-usuario/seu-projeto.git
Compilar e Executar:
Abra o projeto em sua IDE favorita.
Execute a classe Application para iniciar a aplicação.
Testar a API:
Use uma ferramenta como o Postman ou o Insomnia para testar os endpoints da API.
