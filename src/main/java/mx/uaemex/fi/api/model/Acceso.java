package mx.uaemex.fi.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acceso")
public class Acceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "id_empleado", referencedColumnName = "id", nullable = false, unique = true)
    private Empleado empleado;
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;
}
