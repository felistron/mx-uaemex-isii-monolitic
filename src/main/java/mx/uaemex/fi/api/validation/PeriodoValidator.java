package mx.uaemex.fi.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mx.uaemex.fi.api.dto.NominaRequest;

public class PeriodoValidator implements ConstraintValidator<Periodo, NominaRequest> {
    @Override
    public boolean isValid(NominaRequest nominaRequest, ConstraintValidatorContext constraintValidatorContext) {
        return nominaRequest.fechaInicio().isBefore(nominaRequest.fechaFin());
    }
}
