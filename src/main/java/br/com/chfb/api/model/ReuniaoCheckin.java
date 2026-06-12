package br.com.chfb.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "reuniao_checkin",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reuniao_checkin",
                        columnNames = {
                                "reuniao_id",
                                "usuario_id"
                        }
                )
        }
)
public class ReuniaoCheckin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reuniao_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_reuniao_checkin_reuniao"
            )
    )
    private Reuniao reuniao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_reuniao_checkin_usuario"
            )
    )
    private Usuario usuario;

    @Column(
            name = "data_hora_entrada",
            nullable = false
    )
    private LocalDateTime dataHoraEntrada;

    @Column(
            name = "ultima_atividade",
            nullable = false
    )
    private LocalDateTime ultimaAtividade;

    @Column(
            nullable = false
    )
    private Boolean online;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "tipo_acesso",
            nullable = false,
            length = 20
    )
    private TipoAcesso tipoAcesso;

    @Column(
            name = "session_id",
            length = 255
    )
    private String sessionId;

    @Column(
            length = 45
    )
    private String ip;

    @Column(
            name = "user_agent",
            columnDefinition = "TEXT"
    )
    private String userAgent;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.online == null) {
            this.online = true;
        }

        if (this.tipoAcesso == null) {
            this.tipoAcesso = TipoAcesso.WEB;
        }
    }

    @PreUpdate
    public void preUpdate() {

        this.updatedAt = LocalDateTime.now();
    }
}
