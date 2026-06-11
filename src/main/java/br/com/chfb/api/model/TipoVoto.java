package br.com.chfb.api.model;

/**
 * Define a mecânica de escolha permitida ao cooperado para a pauta em votação.
 */
public enum TipoVoto {

    /**
     * Permite a seleção de apenas uma única opção entre as disponíveis na pauta
     * (ex: apenas "Sim", apenas "Não", ou apenas um candidato).
     */
    UNICO,

    /**
     * Permite a seleção de múltiplas opções simultâneas na mesma pauta.
     * Uso comum: Eleições de Conselhos (Administração ou Fiscal), onde o cooperado
     * deve votar em vários nomes para preencher as vagas disponíveis.
     */
    MULTIPLO
}