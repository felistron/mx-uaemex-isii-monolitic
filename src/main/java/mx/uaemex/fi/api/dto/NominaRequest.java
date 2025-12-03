package mx.uaemex.fi.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import mx.uaemex.fi.api.validation.Periodo;

import java.time.LocalDate;

@Periodo
public record NominaRequest(
        @NotNull(message = "El RFC es obligatorio")
        @Pattern(
                regexp = "^[A-Z&Ñ]{4}[0-9]{6}[A-Z0-9]{3}$",
                message = "El RFC debe tener el siguiente formato: LLLLAAMMDDXXX; en donde L=(A-Z) letra o caracteres & o Ñ, AA=año, MM=mes, DD=día, XXX=alfanumérico; todo en mayúsculas y sin espacios"
        )
        String rfc,
        @NotNull(message = "El salario bruto es obligatorio")
        @DecimalMin(message = "El salario bruto debe ser mayor a 0.01", value = "0.01")
        Float salario,
        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDate fechaInicio,
        @NotNull(message = "La fecha de fin es obligatoria")
        LocalDate fechaFin
) {
}
