package mx.uaemex.fi.logic.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.presentation.dto.NominaRequest;
import mx.uaemex.fi.logic.exception.NotFoundException;
import mx.uaemex.fi.persistence.model.Nomina;
import mx.uaemex.fi.persistence.repository.EmpleadoRepository;
import mx.uaemex.fi.persistence.repository.NominaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NominaServiceImp implements NominaService {
    private final NominaRepository nominaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final CalculoNominaService calculoNominaService;

    @Transactional
    @Override
    public void generarNomina(NominaRequest request) throws NotFoundException {
        if (!empleadoRepository.existsByRfc(request.rfc()))
            throw new NotFoundException("Empleado no encontrado");

        var empleado = empleadoRepository.findByRfc(request.rfc());

        var nomina = Nomina.builder()
                .empleado(empleado)
                .salarioBruto(request.salario())
                .excedenteSobreLimiteInferior(calculoNominaService.calcularExcedente(request.salario()))
                .cuotaFija(calculoNominaService.calcularCuotaFija(request.salario()))
                .porcentajeSobreExcedente(calculoNominaService.calcularPorcentaje(request.salario()))
                .periodoInicio(request.fechaInicio())
                .periodoFin(request.fechaFin())
                .build();

        empleado.getNominas().add(nomina);

        empleadoRepository.save(empleado);
    }

    @Override
    public void eliminarNomina(Integer id) throws NotFoundException {
        if (!nominaRepository.existsById(id))
            throw new NotFoundException("Nómina no encontrada");
        nominaRepository.deleteById(id);
    }

    @Override
    public Nomina obtenerNomina(Integer id) throws NotFoundException {
        return nominaRepository.findById(id).orElseThrow(() -> new NotFoundException("Nómina no encontrada"));
    }
}
