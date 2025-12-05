package mx.uaemex.fi.logic.service;

import mx.uaemex.fi.presentation.dto.EmpleadoResponse;
import mx.uaemex.fi.presentation.dto.JwtResponse;
import mx.uaemex.fi.presentation.dto.LoginRequest;
import mx.uaemex.fi.presentation.dto.RegisterRequest;
import mx.uaemex.fi.logic.exception.InvalidCredentialsException;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest) throws InvalidCredentialsException;
    EmpleadoResponse register(RegisterRequest registerRequest);
}
