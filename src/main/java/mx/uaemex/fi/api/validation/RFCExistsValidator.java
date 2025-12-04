package mx.uaemex.fi.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RFCExistsValidator implements ConstraintValidator<RFCExists, String> {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public boolean isValid(String rfc, ConstraintValidatorContext constraintValidatorContext) {
        return empleadoRepository.existsByRfc(rfc);
    }
}
