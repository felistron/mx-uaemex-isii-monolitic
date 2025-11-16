package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.dto.*;
import mx.uaemex.fi.api.model.Empleado;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImp implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImp(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado buscarPorRFC(String rfc) {
        return empleadoRepository.findByRfc(rfc);
    }
}
