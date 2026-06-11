package br.com.chfb.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "pautas",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_pauta_reuniao_ordem",
                        columnNames = {"reuniao_id", "ordem"}
                )
        }
)
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reuniao_id")
    private Reuniao reuniao;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVoto tipoVoto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVotacao tipoVotacao;

    @Column()
    private Integer limiteSelecoes;

    @Column(nullable = false)
    private boolean exigeCodigoVoto = false;

    @Column()
    private String codigoVoto;

    @Column()
    private LocalDateTime dataHoraAbertura;

    @Column()
    private LocalDateTime dataHoraEncerramento;

    @Column()
    private String tempo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPauta status;

    @Column(nullable = false)
    private Integer ordem;

    @OneToMany(mappedBy = "pauta")
    private List<OpcaoVoto> opcoes;

    @PrePersist
    protected void prePersist() {
        if (status == null) {
            status = StatusPauta.AGUARDANDO;
        }
    }
}
