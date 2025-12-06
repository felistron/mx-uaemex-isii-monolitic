package mx.uaemex.fi.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nomina")
public class Nomina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "salario_bruto", nullable = false)
    private Float salarioBruto;

    @Column(name = "excedente", nullable = false)
    private Float excedenteSobreLimiteInferior;

    @Column(name = "cuota_fija", nullable = false)
    private Float cuotaFija;

    @Column(name = "porcentaje", nullable = false)
    private Float porcentajeSobreExcedente;

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @Column(name = "periodo_fin", nullable = false)
    private LocalDate periodoFin;

    @ManyToOne
    @JoinColumn(name = "id_empleado", referencedColumnName = "id", nullable = false)
    private Empleado empleado;
}
