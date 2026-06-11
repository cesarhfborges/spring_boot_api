package br.com.chfb.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column()
    private NivelLog nivel;

    @Column()
    private String origem;

    @Column()
    private String acao;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @Column()
    private Long usuarioId;
}
