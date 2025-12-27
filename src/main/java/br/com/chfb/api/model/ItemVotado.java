package br.com.chfb.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "itens_votados",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"voto_id", "opcao_voto_id"}
        )
)
public class ItemVotado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voto_id")
    private Voto voto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "opcao_voto_id")
    private OpcaoVoto opcaoVoto;
}
