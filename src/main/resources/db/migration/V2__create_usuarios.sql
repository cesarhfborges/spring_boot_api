CREATE TABLE usuarios
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,

    username          VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,

    enabled           BOOLEAN      NOT NULL DEFAULT TRUE,

    account_confirmed BOOLEAN      NOT NULL DEFAULT TRUE,

    CONSTRAINT uk_usuarios_username UNIQUE (username)
);