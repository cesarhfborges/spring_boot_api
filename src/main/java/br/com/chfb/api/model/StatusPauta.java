package br.com.chfb.api.model;

/**
 * Define os estados do ciclo de vida de uma pauta dentro de uma reunião ou assembleia.
 */
public enum StatusPauta {

    /**
     * A pauta está registrada e listada na ordem do dia, mas ainda não foi
     * colocada em discussão ou liberada para votação.
     */
    AGUARDANDO,

    /**
     * A pauta está sendo defendida ou debatida pelos cooperados,
     * mas a coleta de votos ainda não foi iniciada.
     */
    EM_DISCUSSAO,

    /**
     * A pauta está ativa. O debate está liberado ou o sistema está coletando
     * os votos dos cooperados em tempo real.
     */
    ABERTA,

    /**
     * A coleta de votos foi finalizada. O resultado foi apurado com base no
     * quórum estipulado e a pauta não aceita mais modificações ou novos votos.
     */
    ENCERRADA,

    /**
     * A pauta foi retirada da ordem do dia e desconsiderada antes ou durante a
     * reunião, seja por falta de tempo, decisão da mesa ou perda de objeto.
     */
    CANCELADA
}