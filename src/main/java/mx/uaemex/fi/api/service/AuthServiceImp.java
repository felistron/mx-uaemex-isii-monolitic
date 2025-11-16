package mx.uaemex.fi.api.service;

import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.api.dto.*;
import mx.uaemex.fi.api.exception.UserAlreadyExistsException;
import mx.uaemex.fi.api.exception.InvalidCredentialsException;
import mx.uaemex.fi.api.exception.ValidationException;
import mx.uaemex.fi.api.model.Acceso;
import mx.uaemex.fi.api.model.Empleado;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final EmpleadoRepository empleadoRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(LoginRequest request) throws InvalidCredentialsException {
        var exception = new InvalidCredentialsException("Credenciales incorrectas");

        var res = empleadoRepository.findByCorreo(request.correo());
        var empleado = res.stream().findFirst().orElseThrow(() -> exception);

        if (Objects.isNull(empleado.getAcceso())) throw exception;
        var matchPassword = empleado.getAcceso().getHashedPassword();

        if (passwordEncoder.matches(request.password(), matchPassword)) {
            var token = jwtService.generateToken(empleado.getRfc());
            return new JwtResponse(token, "Bearer", jwtService.getExpirationTime());
        }

        throw exception;
    }

    @Transactional
    @Override
    public EmpleadoResponse register(RegisterRequest request) throws ValidationException, UserAlreadyExistsException {
        if (empleadoRepository.existsByCorreo(request.correo())) {
            throw new UserAlreadyExistsException("El correo electrónico ya ha sido registrado por otro empleado");
        }

        if (empleadoRepository.existsByRfc(request.rfc())) {
            throw new UserAlreadyExistsException("El RFC ya ha sido registrado por otro empleado");
        }

        var empleado = Empleado.builder()
                .rfc(request.rfc())
                .nombre(request.nombre())
                .apellidos(request.apellidos())
                .correo(request.correo())
                .build();

        if (!Objects.isNull(request.esAdministrador()) && request.esAdministrador()) {
            if (Objects.isNull(request.password()) && Objects.isNull(request.confirmPassword())) {
                throw new ValidationException("Contraseñas obligatorias");
            }

            if (!request.password().equals(request.confirmPassword())) {
                throw new ValidationException("Las contraseñas no coinciden");
            }

            var hashedPassword = passwordEncoder.encode(request.password());

            var acceso = Acceso.builder()
                    .empleado(empleado)
                    .hashedPassword(hashedPassword)
                    .build();

            empleado.setAcceso(acceso);
        }

        var res = empleadoRepository.save(empleado);

        return new EmpleadoResponse(
                res.getId(),
                res.getRfc(),
                res.getNombre(),
                res.getApellidos(),
                res.getCorreo()
        );
    }
}
