CREATE TABLE votos
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,

    pauta_id       BIGINT   NOT NULL,

    funcionario_id BIGINT   NOT NULL,

    data_hora_voto DATETIME NOT NULL,

    CONSTRAINT uk_voto_pauta_funcionario
        UNIQUE (pauta_id, funcionario_id),

    CONSTRAINT fk_voto_pauta
        FOREIGN KEY (pauta_id)
            REFERENCES pautas (id),

    CONSTRAINT fk_voto_funcionario
        FOREIGN KEY (funcionario_id)
            REFERENCES funcionarios (id)
);