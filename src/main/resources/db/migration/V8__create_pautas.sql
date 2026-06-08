CREATE TABLE pautas
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,

    reuniao_id             BIGINT       NOT NULL,

    titulo                 VARCHAR(255) NOT NULL,

    descricao              TEXT,

    tipo_voto              VARCHAR(20)  NOT NULL,

    limite_selecoes        INT,

    exige_codigo_voto      BOOLEAN      NOT NULL DEFAULT FALSE,

    codigo_voto            VARCHAR(255),

    data_hora_abertura     DATETIME,

    data_hora_encerramento DATETIME,

    status                 VARCHAR(30)  NOT NULL,

    ordem                  INT          NOT NULL,

    CONSTRAINT uk_pauta_reuniao_ordem
        UNIQUE (reuniao_id, ordem),

    CONSTRAINT fk_pauta_reuniao
        FOREIGN KEY (reuniao_id)
            REFERENCES reunioes (id)
);