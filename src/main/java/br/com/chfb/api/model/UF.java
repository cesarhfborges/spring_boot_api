package br.com.chfb.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UF {
    AC, // Acre
    AL, // Alagoas
    AM, // Amazonas
    AP, // Amapá
    BA, // Bahia
    CE, // Ceará
    DF, // Distrito Federal
    ES, // Espírito Santo
    GO, // Goiás
    MA, // Maranhão
    MG, // Minas Gerais
    MS, // Mato Grosso do Sul
    MT, // Mato Grosso
    PA, // Pará
    PB, // Paraíba
    PE, // Pernambuco
    PI, // Piauí
    PR, // Paraná
    RJ, // Rio de Janeiro
    RN, // Rio Grande do Norte
    RO, // Rondônia
    RR, // Roraima
    RS, // Rio Grande do Sul
    SC, // Santa Catarina
    SE, // Sergipe
    SP, // São Paulo
    TO; // Tocantins

    /**
     * Método estático para verificar se uma sigla é válida.
     *
     * @param sigla A string a ser verificada.
     * @return true se for uma sigla de UF válida, false caso contrário.
     */
    public static boolean isValid(String sigla) {
        if (sigla == null) {
            return false;
        }
        try {
            UF.valueOf(sigla.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @JsonCreator
    public static UF fromString(String value) {
        if (value == null) {
            return null;
        }
        try {
            return UF.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "UF inválida: " + value
            );
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
