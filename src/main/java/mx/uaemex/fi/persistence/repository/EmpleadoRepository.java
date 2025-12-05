package mx.uaemex.fi.persistence.repository;

import mx.uaemex.fi.persistence.model.Empleado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
    Empleado findByRfc(String rfc);
    List<Empleado> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByRfc(String rfc);
}
