package mx.uaemex.fi.service;

import mx.uaemex.fi.dto.EmpleadoResponse;
import mx.uaemex.fi.dto.JwtResponse;
import mx.uaemex.fi.dto.LoginRequest;
import mx.uaemex.fi.dto.RegisterRequest;
import mx.uaemex.fi.exception.InvalidCredentialsException;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest) throws InvalidCredentialsException;
    EmpleadoResponse register(RegisterRequest registerRequest);
}
