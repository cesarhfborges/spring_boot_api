CREATE TABLE logs
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    data_hora  datetime              NULL,
    nivel      varchar(255)          NULL,
    origem     VARCHAR(255)          NULL,
    acao       VARCHAR(255)          NULL,
    mensagem   TEXT                  NULL,
    usuario_id BIGINT                NULL,
    CONSTRAINT pk_logs PRIMARY KEY (id)
);