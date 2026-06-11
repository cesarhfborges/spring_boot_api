package br.com.chfb.api.model;

/**
 * Define os canais oficiais de comunicação e contato com o cooperado.
 * Utilizado para o envio de convocações, atas, notificações e avisos legais.
 */
public enum TipoContato {

    /**
     * Endereço eletrônico (E-mail).
     * Canal digital para comunicações formais, envio de editais de convocação e documentos anexos.
     */
    EMAIL,

    /**
     * Linha telefônica (Fixo ou Celular).
     * Canal para contato direto via voz ou envio de mensagens instantâneas/SMS corporativos.
     */
    TELEFONE
}