package br.com.chfb.api.model;

/**
 * Define o tipo de registro e a natureza do voto computado na pauta.
 * Impacta diretamente a base de cálculo dos quóruns de deliberação.
 */
public enum TipoVotoRegistro {

    /**
     * Voto nominal ou direcionado a uma opção válida (ex: "Sim" ou "Não").
     * Contabiliza para a apuração de todos os tipos de votação.
     */
    NORMAL,

    /**
     * O cooperado opta por não escolher nenhuma das opções válidas de forma consciente.
     * Não altera o placar de "Sim/Não", mas conta como presença física.
     * Impacto: Ignorado em MAIORIA_SIMPLES, mas aumenta a base de cálculo (dificultando a aprovação)
     * em MAIORIA_ABSOLUTA e MAIORIA_QUALIFICADA_PRESENTES.
     */
    EM_BRANCO,

    /**
     * Voto considerado inválido por erro, rasura ou anulação intencional.
     * Assim como o voto em branco, conta para o quórum de presença na assembleia,
     * mas é descartado na apuração de maiorias que consideram apenas "votos válidos".
     */
    NULO
}