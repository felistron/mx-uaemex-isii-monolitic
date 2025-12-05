package mx.uaemex.fi.domain.service;

import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.domain.exception.NotFoundException;
import mx.uaemex.fi.persistence.model.Empleado;
import mx.uaemex.fi.persistence.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImp implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public Empleado buscarPorRFC(String rfc) throws NotFoundException {
        if (empleadoRepository.existsByRfc(rfc)) return empleadoRepository.findByRfc(rfc);
        throw new NotFoundException("Empleado no encontrado");
    }

    @Override
    public List<Empleado> obtenerTodos() {
        return (List<Empleado>) empleadoRepository.findAll();
    }
}
