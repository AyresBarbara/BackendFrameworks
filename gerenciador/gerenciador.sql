USE gerenciador;
CREATE TABLE IF NOT EXISTS pessoas (
    id CHAR(36) PRIMARY KEY,
    nome VARCHAR(100),
    idade INT
);
INSERT INTO pessoas (id, nome, idade) VALUES 
(UUID(), 'João', 30),
(UUID(), 'Rafael', 22),
(UUID(), 'Rafael', 45);
