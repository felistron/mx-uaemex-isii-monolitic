package mx.uaemex.fi.service;

public interface CalculoNominaService {
    Float calcularCuotaFija(Float salarioBruto);
    Float calcularExcedente(Float salarioBruto);
    Float calcularPorcentaje(Float salarioBruto);
}
