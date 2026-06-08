CREATE TABLE enderecos
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,

    logradouro     VARCHAR(255),
    numero         VARCHAR(255),
    complemento    VARCHAR(255),
    bairro         VARCHAR(255),
    cidade         VARCHAR(255),

    uf             VARCHAR(2),

    cep            VARCHAR(8),

    funcionario_id BIGINT NOT NULL,

    CONSTRAINT fk_endereco_funcionario
        FOREIGN KEY (funcionario_id)
            REFERENCES funcionarios (id)
);