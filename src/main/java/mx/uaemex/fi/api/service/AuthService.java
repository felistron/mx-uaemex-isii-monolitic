package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.dto.*;
import mx.uaemex.fi.api.exception.InvalidCredentialsException;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest) throws InvalidCredentialsException;
    EmpleadoResponse register(RegisterRequest registerRequest);
}
