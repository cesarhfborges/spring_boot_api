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
        name = "votos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"pauta_id", "funcionario_id"})
)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @Column(nullable = false)
    private LocalDateTime dataHoraVoto;

    @OneToMany(
            mappedBy = "voto",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemVotado> itensVotados;
}
