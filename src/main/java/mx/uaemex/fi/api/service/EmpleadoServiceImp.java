package mx.uaemex.fi.api.service;

import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.api.dto.*;
import mx.uaemex.fi.api.model.Empleado;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImp implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public Empleado buscarPorRFC(String rfc) {
        return empleadoRepository.findByRfc(rfc);
    }

    @Override
    public List<Empleado> obtenerTodos() {
        return (List<Empleado>) empleadoRepository.findAll();
    }
}
