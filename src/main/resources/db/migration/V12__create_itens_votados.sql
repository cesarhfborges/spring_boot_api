CREATE TABLE itens_votados
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,

    voto_id       BIGINT NOT NULL,

    opcao_voto_id BIGINT NOT NULL,

    CONSTRAINT uk_item_votado
        UNIQUE (voto_id, opcao_voto_id),

    CONSTRAINT fk_item_voto
        FOREIGN KEY (voto_id)
            REFERENCES votos (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_item_opcao
        FOREIGN KEY (opcao_voto_id)
            REFERENCES opcoes_voto (id)
);