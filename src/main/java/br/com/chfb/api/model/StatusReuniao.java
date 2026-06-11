package br.com.chfb.api.model;

/**
 * Define os estados do ciclo de vida de uma reunião ou assembleia na cooperativa.
 */
public enum StatusReuniao {

    /**
     * A reunião foi criada, o edital de convocação enviado e a data está definida,
     * mas os trabalhos ainda não foram iniciados.
     */
    AGENDADA,

    /**
     * A sessão está ativa. Os cooperados estão presentes, as pautas estão sendo
     * debatidas e as votações estão ocorrendo em tempo real.
     */
    EM_ANDAMENTO,

    /**
     * A reunião foi concluída. Os trabalhos foram finalizados, as pautas foram
     * apuradas e a ata final foi ou está pronta para ser lavrada.
     */
    ENCERRADA,

    /**
     * A reunião foi temporariamente interrompida (ex: pedido de vista,
     * necessidade de novos pareceres ou estouro de horário) e será retomada
     * em momento posterior sem necessidade de novo edital.
     */
    SUSPENSA,

    /**
     * A reunião foi desmarcada antes do seu início por motivos de força maior,
     * ausência de quórum de instalação ou decisão da diretoria.
     */
    CANCELADA
}