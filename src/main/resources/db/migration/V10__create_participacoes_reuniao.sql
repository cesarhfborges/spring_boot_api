CREATE TABLE participacoes_reuniao
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,

    reuniao_id        BIGINT   NOT NULL,

    funcionario_id    BIGINT   NOT NULL,

    data_hora_entrada DATETIME NOT NULL,

    CONSTRAINT uk_participacao_reuniao
        UNIQUE (reuniao_id, funcionario_id),

    CONSTRAINT fk_participacao_reuniao
        FOREIGN KEY (reuniao_id)
            REFERENCES reunioes (id),

    CONSTRAINT fk_participacao_funcionario
        FOREIGN KEY (funcionario_id)
            REFERENCES funcionarios (id)
);