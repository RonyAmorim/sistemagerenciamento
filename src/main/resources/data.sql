CREATE DATABASE DB_SistemaGerenciamento;

USE DB_SistemaGerenciamento;

-- Tabela de Usuários
CREATE TABLE User (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Type ENUM('Administrador', 'Desenvolvedor') NOT NULL,
    CreationDate DATETIME NOT NULL
);

-- Tabela de Projetos
CREATE TABLE Project (
    ProjectID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Description TEXT,
    INDEX (Name)
);

-- Tabela de Equipes
CREATE TABLE Team (
    TeamID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    ProjectID INT,
    FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID)
);

-- Tabela de Associação Usuário-Equipe
CREATE TABLE UserTeam (
    UserID INT,
    TeamID INT,
    PRIMARY KEY (UserID, TeamID),
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (TeamID) REFERENCES Team(TeamID)
);

-- Tabela de Tarefas
CREATE TABLE Task (
    TaskID INT AUTO_INCREMENT PRIMARY KEY,
    ProjectID INT,
    Name VARCHAR(255) NOT NULL,
    Description TEXT,
    DeadLine DATE,
    Status ENUM('Em Andamento', 'Concluída', 'Atrasada') NOT NULL,
    AssignedTo INT,
    CreationDate DATETIME NOT NULL,
    FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID),
    FOREIGN KEY (AssignedTo) REFERENCES User(UserID),
    INDEX (Status),
    INDEX (DeadLine)
);

-- Tabela de Notificações
CREATE TABLE Notification (
    NotificationID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    TaskID INT,
    Message TEXT NOT NULL,
    DateHour DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (TaskID) REFERENCES Task(TaskID),
    INDEX (DateHour)
);

-- Tabela de Impedimentos
CREATE TABLE Impediment (
    ImpedimentID INT AUTO_INCREMENT PRIMARY KEY,
    TaskID INT,
    Description TEXT NOT NULL,
    ReportedBy INT,
    CreationDate DATETIME NOT NULL,
    ResolutionDate DATETIME,
    FOREIGN KEY (TaskID) REFERENCES Task(TaskID),
    FOREIGN KEY (ReportedBy) REFERENCES User(UserID)
);

-- Tabela de Comentários
CREATE TABLE Comment (
    CommentID INT AUTO_INCREMENT PRIMARY KEY,
    TaskID INT,
    UserID INT,
    Text TEXT NOT NULL,
    DateHour DATETIME NOT NULL,
    FOREIGN KEY (TaskID) REFERENCES Task(TaskID),
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    INDEX (DateHour)
);
