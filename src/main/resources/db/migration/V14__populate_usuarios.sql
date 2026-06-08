-- usuário

INSERT INTO usuarios (
    username,
    password,
    enabled,
    account_confirmed
)
SELECT
    'admin@admin.com',
    '$2a$10$SEU_HASH_AQUI',
    TRUE,
    TRUE
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM usuarios
    WHERE username = 'admin@admin.com'
);

-- vincula role admin

INSERT INTO usuarios_roles (
    usuario_id,
    role_id
)
SELECT
    u.id,
    r.id
FROM usuarios u
         JOIN roles r
              ON r.name = 'ROLE_ADMIN'
WHERE u.username = 'admin@admin.com'
  AND NOT EXISTS (
    SELECT 1
    FROM usuarios_roles ur
    WHERE ur.usuario_id = u.id
      AND ur.role_id = r.id
);

-- funcionário

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
WHERE u.username = 'admin@admin.com'
  AND NOT EXISTS (
    SELECT 1
    FROM funcionarios f
    WHERE f.user_id = u.id
);

-- contato

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
         JOIN usuarios u
              ON u.id = f.user_id
WHERE u.username = 'admin@admin.com'
  AND NOT EXISTS (
    SELECT 1
    FROM contatos c
    WHERE c.funcionario_id = f.id
      AND c.tipo = 'EMAIL'
      AND c.valor = 'admin@sistema.com'
);