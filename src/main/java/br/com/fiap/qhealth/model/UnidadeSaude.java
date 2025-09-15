package br.com.fiap.qhealth.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unidade_saude", schema = "ubs")
public class UnidadeSaude {

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String nome;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_alteracao")
    private LocalDateTime dataUltimaAlteracao;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = now();
        this.dataUltimaAlteracao = now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataUltimaAlteracao = now();
    }
}
