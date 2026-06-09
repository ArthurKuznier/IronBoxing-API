INSERT INTO plano (nome, valor) VALUES
('Bronze', 50.00),
('Prata', 80.00),
('Ouro', 120.00);

-- Inserts de Usuários (Professores, Atletas e Administrador)
-- ID 1: João Silva (Treinador)
-- ID 2: Maria Santos (Treinador)
-- ID 3: Pedro Santos (Treinador)
-- ID 4: João Silva (Atleta)
-- ID 5: Maria Santos (Atleta)
-- ID 6: Admin Geral (Admin)
INSERT INTO usuario (nome, email, senha, cpf, telefone, endereco, cep, cidade, estado, data_nascimento, genero, role) VALUES
('João Silva Professor', 'joao.prof@ironboxing.com', '123456', '123.456.789-09', '(11) 99999-9991', 'Av Paulista, 1000', '01310100', 'São Paulo', 'SP', '1985-05-10', 'M', 'TREINADOR'),
('Maria Santos Professora', 'maria.prof@ironboxing.com', '123456', '987.654.321-00', '(11) 99999-9992', 'Rua Augusta, 500', '01305000', 'São Paulo', 'SP', '1990-08-15', 'F', 'TREINADOR'),
('Pedro Santos Professor', 'pedro.prof@ironboxing.com', '123456', '111.222.333-96', '(11) 99999-9993', 'Rua Pamplona, 300', '01405000', 'São Paulo', 'SP', '1988-12-20', 'M', 'TREINADOR'),
('João Silva Atleta', 'joao.atleta@gmail.com', '123456', '222.333.444-05', '(11) 98888-8881', 'Rua das Flores, 10', '02001000', 'São Paulo', 'SP', '1995-03-25', 'M', 'ATLETA'),
('Maria Santos Atleta', 'maria.atleta@gmail.com', '123456', '555.666.777-20', '(11) 98888-8882', 'Rua das Palmeiras, 20', '02002000', 'São Paulo', 'SP', '1998-07-30', 'F', 'ATLETA'),
('Admin Geral', 'admin@ironboxing.com', '123456', '999.999.999-99', '(11) 99999-9999', 'Av Brigadeiro, 2000', '01311300', 'São Paulo', 'SP', '1980-01-01', 'M', 'ADMIN');

-- Perfis de Treinadores
INSERT INTO treinador (usuario_id, cref, especialidade) VALUES
(1, '123456-SP', 'Musculação'),
(2, '789012-SP', 'Cardio'),
(3, '345678-SP', 'Funcional');

-- Turmas
INSERT INTO turma (descricao, horario, treinador_id) VALUES
('Musculação', '06:00:00', 1),
('Cardio', '07:00:00', 2),
('Funcional', '08:00:00', 3);

-- Perfis de Atletas
INSERT INTO atleta (usuario_id) VALUES
(4),
(5);

-- Matrículas
INSERT INTO matricula (atleta_id, turma_id, plano_id, data_matricula, situacao_mat) VALUES
(1, 1, 1, '2023-01-01', 'A'),
(2, 2, 2, '2023-01-01', 'A');

-- Avaliações Físicas
INSERT INTO avaliacao_fisica (atleta_id, data_avaliacao, peso, altura, imc) VALUES
(1, '2023-01-01', 70.00, 170, 24.21),
(2, '2023-01-01', 60.00, 160, 23.44);

-- Pagamentos
INSERT INTO pagamento (matricula_id, data_pagamento, valor_pago, forma_pagamento, situacao) VALUES
(1, '2023-01-01 10:00:00', 50.00, 'Cartão', 'P'),
(2, '2023-01-01 10:30:00', 80.00, 'Boleto', 'P');