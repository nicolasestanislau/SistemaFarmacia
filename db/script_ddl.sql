CREATE TABLE medicamento (
    id INT AUTO_INCREMENT NOT NULL,
    nome char(100),
    preco double,
    vencimento date,
    PRIMARY KEY(id)
);

CREATE TABLE funcionario (
    id INT AUTO_INCREMENT NOT NULL,
    nome char(100),
    cargo char(100),
    salario double,
    PRIMARY KEY(id)
);