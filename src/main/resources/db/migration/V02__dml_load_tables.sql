-- Inserindo endereços
INSERT INTO ubs.endereco (rua, numero, cep, complemento, bairro, cidade)
VALUES
('Rua das Flores', 123, '12345678', 'Apto 101', 'Centro', 'São Paulo'),
('Av. Brasil', 456, '98765432', NULL, 'Jardins', 'São Paulo');

-- Inserindo unidades de saúde com os endereços acima
INSERT INTO ubs.unidade_saude (nome, endereco_id)
VALUES
('UBS Jardim Saúde', (SELECT id FROM ubs.endereco WHERE rua = 'Rua das Flores' LIMIT 1)),
('Posto Central', (SELECT id FROM ubs.endereco WHERE rua = 'Av. Brasil' LIMIT 1));
