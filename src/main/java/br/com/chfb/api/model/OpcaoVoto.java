package br.com.chfb.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "opcoes_voto")
public class OpcaoVoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column()
    private String icone;

    @Column()
    private Integer ordem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVotoRegistro tipo;
}
