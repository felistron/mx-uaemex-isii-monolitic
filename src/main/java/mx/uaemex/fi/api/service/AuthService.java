package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.dto.*;
import mx.uaemex.fi.api.exception.UserAlreadyExistsException;
import mx.uaemex.fi.api.exception.InvalidCredentialsException;
import mx.uaemex.fi.api.exception.ValidationException;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest) throws InvalidCredentialsException;
    EmpleadoResponse register(RegisterRequest registerRequest) throws ValidationException, UserAlreadyExistsException;
}
