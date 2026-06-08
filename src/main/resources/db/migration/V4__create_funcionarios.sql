CREATE TABLE funcionarios
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,

    nome            VARCHAR(255) NOT NULL,
    sobrenome       VARCHAR(255) NOT NULL,

    data_nascimento DATE,

    cpf             VARCHAR(11) UNIQUE,
    rg              VARCHAR(20) UNIQUE,

    user_id         BIGINT       NOT NULL UNIQUE,

    CONSTRAINT fk_funcionario_usuario
        FOREIGN KEY (user_id)
            REFERENCES usuarios (id)
);