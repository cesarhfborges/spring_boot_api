CREATE TABLE pautas
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    reuniao_id             BIGINT                                                  NOT NULL,
    ordem                  INT                                                     NOT NULL default 1,
    titulo                 VARCHAR(255)                                            NOT NULL,
    descricao              TEXT,

    tipo_voto              enum ('UNICO', 'MULTIPLO')                              NOT NULL DEFAULT 'UNICO',
    tipoVotacao            enum (
        'MAIORIA_SIMPLES',
        'MAIORIA_ABSOLUTA',
        'MAIORIA_QUALIFICADA_PRESENTES',
        'MAIORIA_QUALIFICADA_GERAL',
        'UNANIMIDADE'
        )                                                                          NOT NULL DEFAULT 'MAIORIA_ABSOLUTA',

    limite_selecoes        INT                                                              DEFAULT 1,

    exige_codigo_voto      BOOLEAN                                                 NOT NULL DEFAULT FALSE,
    codigo_voto            VARCHAR(255),

    tempo                  VARCHAR(8)                                                       DEFAULT NULL,
    data_hora_abertura     DATETIME,
    data_hora_encerramento DATETIME,

    status                 enum ('AGUARDANDO', 'ABERTA', 'ENCERRADA', 'CANCELADA') NOT NULL DEFAULT 'AGUARDANDO',

    CONSTRAINT chk_pauta_limite_selecoes
        CHECK (limite_selecoes >= 1),

    CONSTRAINT uk_pauta_reuniao_ordem
        UNIQUE (reuniao_id, ordem),

    CONSTRAINT fk_pauta_reuniao
        FOREIGN KEY (reuniao_id)
            REFERENCES reunioes (id)
);