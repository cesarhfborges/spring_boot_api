package br.com.chfb.api.model;

/**
 * Define os critérios de quórum de deliberação para apuração dos resultados
 * das votações de pautas em assembleias da cooperativa.
 */
public enum TipoVotacao {

    /**
     * BASE: MEMBROS PRESENTES (Apenas os Votos Válidos).
     * O resultado é aprovado se o número de votos "Sim" for maior que os votos "Não".
     * Ignora abstenções e votos em branco.
     * Uso: Deliberações comuns e rotineiras.
     */
    MAIORIA_SIMPLES,

    /**
     * BASE: MEMBROS PRESENTES (Total de CPFs na sessão).
     * Exige o primeiro número inteiro superior à metade de todos os cooperados
     * que assinaram a lista de presença. Votos em branco e abstenções reduzem
     * a chance de aprovação.
     * Uso: Matérias de relevância intermediária.
     */
    MAIORIA_ABSOLUTA,

    /**
     * BASE: MEMBROS PRESENTES (Percentual Qualificado).
     * Exige uma fração específica (comumente 2/3) dos votos dos cooperados presentes na sessão.
     * Uso Legal Obrigatório: Exigido pelo Art. 46 da Lei 5.764/71 para reforma de estatuto,
     * fusão, incorporação, desmembramento, dissolução ou mudança do objeto da sociedade.
     */
    MAIORIA_QUALIFICADA_PRESENTES,

    /**
     * BASE: QUADRO GERAL (Todos os Associados da Cooperativa).
     * Exige um percentual fixo (ex: 2/3 ou metade + 1) calculado sobre a totalidade de
     * membros ativos da cooperativa, independentemente de quantos compareceram à assembleia.
     * Uso: Casos críticos definidos em Estatuto, como decisões de alto risco patrimonial.
     */
    MAIORIA_QUALIFICADA_GERAL,

    /**
     * BASE: MEMBROS PRESENTES (100% de Aprovação).
     * Exige que absolutamente todos os cooperados aptos a votar e presentes na sessão
     * votem a favor.
     * Uso: Casos excepcionais ou restrições estatutárias severas.
     */
    UNANIMIDADE
}