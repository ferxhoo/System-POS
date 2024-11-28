package system.pos.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Caja")
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCaja;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime fechaApertura;

    @Column(precision = 19, scale = 2)
    private BigDecimal montoInicial;

    private LocalDateTime fechaCierre;
    
    @Column(precision = 19, scale = 2)
    private BigDecimal montoFinal;

    private String estado;

    @PrePersist
    private void inicializarFechaApertura() {
        this.fechaApertura = LocalDateTime.now();
    }

    public void cerrarCaja(BigDecimal montoFinal) {
        this.fechaCierre = LocalDateTime.now();
        this.montoFinal = montoFinal;
    }
}


