-- Usuário administrador

INSERT INTO usuarios (
    username,
    password,
    enabled,
    account_confirmed
)
VALUES (
           'admin@admin.com',
           '$2a$10$JdAHDvDfqerxmc/t82/dRuQuhwL1DrZK.wW3HUFFfuk..8zo.l14W',
           TRUE,
           TRUE
       );

-- Perfil administrador

INSERT INTO usuarios_roles (
    usuario_id,
    role_id
)
SELECT
    u.id,
    r.id
FROM usuarios u
         JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.username = 'admin@admin.com';

-- Funcionário administrador

INSERT INTO funcionarios (
    nome,
    sobrenome,
    cpf,
    rg,
    user_id
)
SELECT
    'Administrador',
    'Sistema',
    '00000000000',
    '000000000',
    u.id
FROM usuarios u
WHERE u.username = 'admin@admin.com';

-- Contato administrador

INSERT INTO contatos (
    tipo,
    valor,
    funcionario_id
)
SELECT
    'EMAIL',
    'admin@sistema.com',
    f.id
FROM funcionarios f
         JOIN usuarios u ON u.id = f.user_id
WHERE u.username = 'admin@admin.com';