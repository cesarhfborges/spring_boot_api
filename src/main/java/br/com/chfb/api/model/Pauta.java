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

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVoto tipoVoto;

    private Integer limiteSelecoes;

    @Column(nullable = false)
    private boolean exigeCodigoVoto = false;

    private String codigoVoto;

    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dataHoraEncerramento;

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

        if (codigoVoto == null) {
            codigoVoto = "";
        }
    }
}
