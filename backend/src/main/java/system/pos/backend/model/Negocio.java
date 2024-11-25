package system.pos.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DatosNegocio")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Negocio {

    @Id
    private String nit;

    @Column(nullable = false)
    private String nombreNegocio;

    private String direccion;
    private String pais;
    private String departamento;
    private String ciudad;
    private String telefono;
    private String email;

    @Column(nullable = false)
    private String representanteLegal;

    @Column(nullable = false)
    private String tipoRegimen;

    @Column(nullable = false)
    private String responsabilidadFiscal;

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
