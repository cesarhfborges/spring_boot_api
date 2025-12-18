package br.com.chfb.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contatos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoContato tipo;

    @Column(nullable = false)
    private String valor; // email ou telefone

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;
}