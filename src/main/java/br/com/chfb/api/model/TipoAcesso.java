package br.com.chfb.api.model;

public enum TipoAcesso {
    NONE,
    WEB,
    MOBILE,
    TABLET;

    public static TipoAcesso from(String valor) {

        if (valor == null || valor.isBlank()) {
            return NONE;
        }

        try {
            return valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }
}
