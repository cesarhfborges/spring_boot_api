CREATE TABLE IF NOT EXISTS reuniao_checkin
(
    id BIGINT NOT NULL AUTO_INCREMENT,

    reuniao_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,

    data_hora_entrada DATETIME(6) NOT NULL,

    ultima_atividade DATETIME(6) NOT NULL,

    online BOOLEAN NOT NULL DEFAULT TRUE,

    tipo_acesso ENUM (
        'NONE',
        'WEB',
        'MOBILE',
        'TABLET'
        ) NOT NULL DEFAULT 'WEB',

    session_id VARCHAR(255) NULL,

    ip VARCHAR(45) NULL,

    user_agent LONGTEXT NULL,

    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
        ON UPDATE CURRENT_TIMESTAMP(6),

    PRIMARY KEY (id),

    CONSTRAINT uk_reuniao_checkin
        UNIQUE (reuniao_id, usuario_id),

    CONSTRAINT fk_reuniao_checkin_reuniao
        FOREIGN KEY (reuniao_id)
            REFERENCES reunioes (id),

    CONSTRAINT fk_reuniao_checkin_usuario
        FOREIGN KEY (usuario_id)
            REFERENCES usuarios (id)
);

CREATE INDEX idx_reuniao_checkin_reuniao
    ON reuniao_checkin(reuniao_id);

CREATE INDEX idx_reuniao_checkin_usuario
    ON reuniao_checkin(usuario_id);

CREATE INDEX idx_reuniao_checkin_online
    ON reuniao_checkin(online);

CREATE INDEX idx_reuniao_checkin_atividade
    ON reuniao_checkin(ultima_atividade);