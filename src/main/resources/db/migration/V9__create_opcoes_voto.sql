CREATE TABLE opcoes_voto
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    pauta_id  BIGINT       NOT NULL,
    titulo    VARCHAR(255) NOT NULL,
    descricao TEXT,
    icone     VARCHAR(255),
    ordem     INT,
    tipo      ENUM('NORMAL','EM_BRANCO','NULO') NOT NULL DEFAULT 'NORMAL',
    CONSTRAINT fk_opcao_pauta
        FOREIGN KEY (pauta_id)
            REFERENCES pautas (id)
);