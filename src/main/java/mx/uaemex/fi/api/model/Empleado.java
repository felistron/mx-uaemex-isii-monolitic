package mx.uaemex.fi.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String rfc;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column(unique = true, nullable = false)
    private String correo;

    @OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL)
    private Acceso acceso;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Nomina> nominas;
}
