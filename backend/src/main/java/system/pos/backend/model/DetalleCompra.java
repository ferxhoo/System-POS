package system.pos.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DetallesCompra")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleCompra;

    @ManyToOne
    @JoinColumn(name = "idCompra")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "idInsumo")
    private Insumo insumo;

    private Integer cantidad;
    
    @Column(precision = 19, scale = 2)
    private BigDecimal precioUnitario;
    
    @Column(precision = 19, scale = 2)
    private BigDecimal subtotal;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime ultimaActualizacion;

    @PrePersist
    private void inicializarFechas() {
        this.fechaRegistro = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    private void actualizarUltimaActualizacion() {
        this.ultimaActualizacion = LocalDateTime.now();
    }
}