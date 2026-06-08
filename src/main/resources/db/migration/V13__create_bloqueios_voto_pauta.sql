CREATE TABLE bloqueios_voto_pauta
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,

    funcionario_id BIGINT       NOT NULL,

    pauta_id       BIGINT       NOT NULL,

    motivo         VARCHAR(255) NOT NULL,

    data_inclusao  DATETIME     NOT NULL,

    incluido_por   BIGINT,

    ativo          BOOLEAN      NOT NULL DEFAULT TRUE,

    CONSTRAINT uk_bloqueio_voto_pauta
        UNIQUE (funcionario_id, pauta_id),

    CONSTRAINT fk_bloqueio_funcionario
        FOREIGN KEY (funcionario_id)
            REFERENCES funcionarios (id),

    CONSTRAINT fk_bloqueio_pauta
        FOREIGN KEY (pauta_id)
            REFERENCES pautas (id),

    CONSTRAINT fk_bloqueio_usuario
        FOREIGN KEY (incluido_por)
            REFERENCES usuarios (id)
);