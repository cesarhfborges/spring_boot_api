# Insert Reunião

insert into reunioes (titulo, descricao, data_hora_inicio, data_hora_fim, status, criado_por)
values ('Assembleia Geral Ordinária (AGO)',
        'É a reunião anual obrigatória, realizada sempre nos quatro primeiros meses após o término do exercício social. Sua função é prestar contas, avaliar os resultados financeiros, aprovar o balanço do ano anterior, decidir a destinação das sobras e eleger os membros do conselho.',
        adddate(NOW(), 1),
        null,
        'AGENDADA',
        (SELECT u.id FROM usuarios as u ORDER BY u.id LIMIT 1));

-- =====================================
-- Pauta 1
-- Eleição do Conselho Administração
-- =====================================

INSERT INTO pautas (
    reuniao_id,
    titulo,
    descricao,
    tipo_voto,
    ordem
) VALUES (
             (SELECT r.id FROM reunioes as r ORDER BY r.id ASC LIMIT 1),
             'Aprovação das Contas do Exercício',
             'Deliberação sobre o relatório de gestão, demonstrações financeiras e parecer do conselho fiscal referentes ao exercício encerrado.',
             'UNICO',
             1
         );

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Aprovar', 1, 'NORMAL'
FROM pautas
WHERE titulo = 'Aprovação das Contas do Exercício';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Desaprovar', 2, 'NORMAL'
FROM pautas
WHERE titulo = 'Aprovação das Contas do Exercício';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Em Branco', 3, 'EM_BRANCO'
FROM pautas
WHERE titulo = 'Aprovação das Contas do Exercício';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Nulo', 4, 'NULO'
FROM pautas
WHERE titulo = 'Aprovação das Contas do Exercício';

-- =====================================
-- Pauta 2
-- Destinação das Sobras do Exercício
-- =====================================
INSERT INTO pautas (reuniao_id,
                    titulo,
                    descricao,
                    tipo_voto,
                    limite_selecoes,
                    status,
                    ordem)
VALUES ((SELECT r.id FROM reunioes as r ORDER BY r.id ASC LIMIT 1),
        'Destinação das Sobras do Exercício',
        'Definição da aplicação das sobras apuradas, podendo ser distribuídas aos cooperados ou destinadas aos fundos obrigatórios.',
        'UNICO',
        1,
        'AGUARDANDO',
        2);

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Aprovar', 1, 'NORMAL'
FROM pautas
WHERE titulo = 'Destinação das Sobras do Exercício';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Desaprovar', 2, 'NORMAL'
FROM pautas
WHERE titulo = 'Destinação das Sobras do Exercício';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Em Branco', 3, 'EM_BRANCO'
FROM pautas
WHERE titulo = 'Destinação das Sobras do Exercício';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Nulo', 4, 'NULO'
FROM pautas
WHERE titulo = 'Destinação das Sobras do Exercício';

-- =====================================
-- Pauta 3
-- Eleição do Conselho Administração
-- =====================================
INSERT INTO pautas (reuniao_id,
                    titulo,
                    descricao,
                    tipo_voto,
                    limite_selecoes,
                    status,
                    ordem)
VALUES ((SELECT r.id FROM reunioes as r ORDER BY r.id ASC LIMIT 1),
        'Eleição do Conselho de Administração',
        'Escolha dos membros responsáveis pela gestão estratégica da cooperativa para o próximo mandato.',
        'UNICO',
        1,
        'AGUARDANDO',
        3);

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Carlos Henrique Martins', 1, 'NORMAL'
FROM pautas
WHERE titulo = 'Eleição do Conselho de Administração';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Mariana Oliveira Souza', 2, 'NORMAL'
FROM pautas
WHERE titulo = 'Eleição do Conselho de Administração';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Ricardo Almeida Santos', 3, 'NORMAL'
FROM pautas
WHERE titulo = 'Eleição do Conselho de Administração';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Em Branco', 4, 'EM_BRANCO'
FROM pautas
WHERE titulo = 'Eleição do Conselho de Administração';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Nulo', 5, 'NULO'
FROM pautas
WHERE titulo = 'Eleição do Conselho de Administração';

-- =====================================
-- Pauta 4
-- Eleição do Conselho Fiscal
-- =====================================
INSERT INTO pautas (reuniao_id,
                    titulo,
                    descricao,
                    tipo_voto,
                    limite_selecoes,
                    status,
                    ordem)
VALUES ((SELECT r.id FROM reunioes as r ORDER BY r.id ASC LIMIT 1),
        'Eleição do Conselho Fiscal',
        'Escolha dos membros titulares e suplentes responsáveis pela fiscalização das contas e atos administrativos da cooperativa.',
        'UNICO',
        1,
        'AGUARDANDO',
        4);

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Fernanda Costa Lima', 1, 'NORMAL'
FROM pautas
WHERE titulo = 'Eleição do Conselho Fiscal';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Paulo Roberto Ferreira', 2, 'NORMAL'
FROM pautas
WHERE titulo = 'Eleição do Conselho Fiscal';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Juliana Mendes Rocha', 3, 'NORMAL'
FROM pautas
WHERE titulo = 'Eleição do Conselho Fiscal';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Em Branco', 4, 'EM_BRANCO'
FROM pautas
WHERE titulo = 'Eleição do Conselho Fiscal';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Nulo', 5, 'NULO'
FROM pautas
WHERE titulo = 'Eleição do Conselho Fiscal';

-- =====================================
-- Pauta 5
-- Aprovação do Plano de Investimentos
-- =====================================
INSERT INTO pautas (reuniao_id,
                    titulo,
                    descricao,
                    tipo_voto,
                    limite_selecoes,
                    status,
                    ordem)
VALUES ((SELECT r.id FROM reunioes as r ORDER BY r.id ASC LIMIT 1),
        'Aprovação do Plano de Investimentos',
        'Deliberação sobre investimentos em infraestrutura, tecnologia e expansão dos serviços da cooperativa para o próximo exercício.',
        'UNICO',
        1,
        'AGUARDANDO',
        5);

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Aprovar', 1, 'NORMAL'
FROM pautas
WHERE titulo = 'Aprovação do Plano de Investimentos';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Desaprovar', 2, 'NORMAL'
FROM pautas
WHERE titulo = 'Aprovação do Plano de Investimentos';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Em Branco', 3, 'EM_BRANCO'
FROM pautas
WHERE titulo = 'Aprovação do Plano de Investimentos';

INSERT INTO opcoes_voto (pauta_id, titulo, ordem, tipo)
SELECT id, 'Nulo', 4, 'NULO'
FROM pautas
WHERE titulo = 'Aprovação do Plano de Investimentos';
