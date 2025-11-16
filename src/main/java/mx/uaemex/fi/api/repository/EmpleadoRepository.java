package mx.uaemex.fi.api.repository;

import mx.uaemex.fi.api.model.Empleado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
    Empleado findByRfc(String rfc);
    List<Empleado> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByRfc(String rfc);
}
