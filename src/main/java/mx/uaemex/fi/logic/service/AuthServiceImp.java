package mx.uaemex.fi.logic.service;

import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.presentation.dto.EmpleadoResponse;
import mx.uaemex.fi.presentation.dto.JwtResponse;
import mx.uaemex.fi.presentation.dto.LoginRequest;
import mx.uaemex.fi.presentation.dto.RegisterRequest;
import mx.uaemex.fi.logic.exception.InvalidCredentialsException;
import mx.uaemex.fi.persistence.model.Acceso;
import mx.uaemex.fi.persistence.model.Empleado;
import mx.uaemex.fi.persistence.repository.EmpleadoRepository;
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
    public EmpleadoResponse register(RegisterRequest request) {
        var empleado = Empleado.builder()
                .rfc(request.rfc())
                .nombre(request.nombre())
                .apellidos(request.apellidos())
                .correo(request.correo())
                .build();

        if (!Objects.isNull(request.esAdministrador()) && request.esAdministrador()) {
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
