CREATE TABLE reunioes
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,

    titulo           VARCHAR(255) NOT NULL,

    descricao        TEXT,

    data_hora_inicio DATETIME     NOT NULL,

    data_hora_fim    DATETIME,

    status           VARCHAR(30)  NOT NULL DEFAULT 'AGENDADA',

    criado_por       BIGINT,

    CONSTRAINT fk_reuniao_usuario
        FOREIGN KEY (criado_por)
            REFERENCES usuarios (id)
);