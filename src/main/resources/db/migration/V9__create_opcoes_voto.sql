CREATE TABLE opcoes_voto
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,

    pauta_id  BIGINT       NOT NULL,

    titulo    VARCHAR(255) NOT NULL,

    descricao TEXT,

    icone     VARCHAR(255),

    ordem     INT,

    tipo      VARCHAR(20)  NOT NULL,

    CONSTRAINT fk_opcao_pauta
        FOREIGN KEY (pauta_id)
            REFERENCES pautas (id)
);