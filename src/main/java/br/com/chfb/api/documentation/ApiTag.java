package br.com.chfb.api.documentation;

public enum ApiTag {

    AUTH(1, "Autenticação", "Endpoints de login e logout"),
    PERFIL(2, "Perfil", "Atualização de perfil"),
    FUNCIONARIO(3, "Funcionario", "CRUD de funcionarios do sistema"),
    ENDERECOS(4, "Endereços", "CRUD de endereços do sistema"),
    CONTATOS(5, "Contatos", "CRUD de contatos do sistema"),
    REUNIAO(6, "Reuniao", "Gerenciamento de reuniões"),
    PAUTAS(7, "Pautas", "Gestão de pautas"),
    BLOQUEIO(8, "Bloqueio", "Controle de bloqueios"),
    VOTO(9, "Voto", "Registro e apuração de votos");

    private final int order;
    private final String label;
    private final String description;

    ApiTag(int order, String label, String description) {
        this.order = order;
        this.label = label;
        this.description = description;
    }

    /**
     * Texto exibido no Swagger
     */
    public String displayName() {
        return String.format("%02d - %s", order, label);
    }

    /**
     * Descrição exibida no Swagger
     */
    public String description() {
        return description;
    }

    /**
     * Ordem numérica da tag
     */
    public int order() {
        return order;
    }
}
