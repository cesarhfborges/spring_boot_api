package br.com.chfb.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "reunioes")
public class Reuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataHoraInicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(30) default 'AGENDADA'")
    private StatusReuniao status;

    @ManyToOne
    @JoinColumn(name = "criado_por")
    private Usuario criadoPor;

    @OneToMany(mappedBy = "reuniao")
    private List<Pauta> pautas;

    @PrePersist
    protected void prePersist() {
        if (status == null) {
            status = StatusReuniao.AGENDADA;
        }
    }
}
