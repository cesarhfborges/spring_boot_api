CREATE TABLE contatos
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,

    tipo           VARCHAR(20)  NOT NULL,

    valor          VARCHAR(255) NOT NULL,

    funcionario_id BIGINT       NOT NULL,

    CONSTRAINT fk_contato_funcionario
        FOREIGN KEY (funcionario_id)
            REFERENCES funcionarios (id)
);