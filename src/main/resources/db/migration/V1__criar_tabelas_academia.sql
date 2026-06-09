CREATE TABLE usuario(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(15) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    data_nascimento DATE NOT NULL,
    genero CHAR(1) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN','ATLETA','TREINADOR'))
);

CREATE TABLE plano (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    valor DECIMAL(12,2) NOT NULL
);

CREATE TABLE treinador (
    id SERIAL PRIMARY KEY,
    cref VARCHAR(20) NOT NULL,
    especialidade VARCHAR(50) NOT NULL,
    usuario_id INT NOT NULL UNIQUE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE turma (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR (100) NOT NULL,
    horario TIME NOT NULL,
    treinador_id INT NOT NULL,
    FOREIGN KEY (treinador_id) REFERENCES treinador(id)
);

CREATE TABLE atleta (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL UNIQUE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE matricula (
    id SERIAL PRIMARY KEY,
    atleta_id INT NOT NULL,
    FOREIGN KEY (atleta_id) REFERENCES atleta(id),
    turma_id INT NOT NULL,
    FOREIGN KEY (turma_id) REFERENCES turma(id),
    data_matricula DATE NOT NULL,
    plano_id INT NOT NULL,
    FOREIGN KEY (plano_id) REFERENCES plano(id),
    situacao_mat CHAR(1) NOT NULL CHECK (situacao_mat IN ('A','I'))
);

CREATE TABLE avaliacao_fisica (
    id SERIAL PRIMARY KEY,
    atleta_id INT NOT NULL,
    FOREIGN KEY (atleta_id) REFERENCES atleta(id),
    data_avaliacao DATE NOT NULL,
    peso DECIMAL(12,2) NOT NULL,
    altura INT NOT NULL,
    imc DECIMAL(12,2) NOT NULL
);

CREATE TABLE pagamento (
    id SERIAL PRIMARY KEY,
    matricula_id INT NOT NULL,
    FOREIGN KEY (matricula_id) REFERENCES matricula(id),
    data_pagamento TIMESTAMP,
    valor_pago DECIMAL(12,2) NOT NULL,
    forma_pagamento VARCHAR(20) NOT NULL,
    situacao CHAR(1) NOT NULL CHECK (situacao IN ('P','A'))
);
