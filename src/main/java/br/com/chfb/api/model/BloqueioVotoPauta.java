package br.com.chfb.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "bloqueios_voto_pauta",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"funcionario_id", "pauta_id"}
        )
)
public class BloqueioVotoPauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private LocalDateTime dataInclusao;

    @ManyToOne
    @JoinColumn(name = "incluido_por")
    private Usuario incluidoPor;

    @Column(nullable = false)
    private boolean ativo = true;
}