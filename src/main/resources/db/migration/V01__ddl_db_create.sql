CREATE SCHEMA IF NOT EXISTS ubs;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE ubs.endereco (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    rua VARCHAR(255) NOT NULL,
    numero INTEGER NOT NULL,
    cep VARCHAR(8) NOT NULL,
    complemento VARCHAR(255),
    bairro VARCHAR(255) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_alteracao TIMESTAMP
);

CREATE TABLE ubs.unidade_saude (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    endereco_id UUID NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_alteracao TIMESTAMP,
    
    CONSTRAINT fk_unidade_endereco FOREIGN KEY (endereco_id)
        REFERENCES ubs.endereco(id)
);

