package br.com.chfb.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "participacoes_reuniao",
        uniqueConstraints = @UniqueConstraint(columnNames = {"reuniao_id", "funcionario_id"})
)
public class ParticipacaoReuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reuniao_id")
    private Reuniao reuniao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @Column(nullable = false)
    private LocalDateTime dataHoraEntrada;
}
