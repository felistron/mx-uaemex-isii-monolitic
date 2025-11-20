package mx.uaemex.fi.api.controller;

import mx.uaemex.fi.api.dto.EmpleadoResponse;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import mx.uaemex.fi.api.service.AuthService;
import mx.uaemex.fi.api.service.CustomUserDetailsService;
import mx.uaemex.fi.api.service.JwtServiceImp;
import mx.uaemex.fi.api.validation.ConditionalPasswordValidator;
import mx.uaemex.fi.api.validation.UniqueEmailValidator;
import mx.uaemex.fi.api.validation.UniqueRFCValidator;
import mx.uaemex.fi.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, UniqueRFCValidator.class, UniqueEmailValidator.class, ConditionalPasswordValidator.class})
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtServiceImp jwtService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @MockitoBean
    private EmpleadoRepository empleadoRepository;

    @Test
    void obtenerPaginaDeRegistro() throws Exception {
        mockMvc.perform(
                    get("/auth/registrar")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register")
                );
    }

    @Test
    void registrarEmpleadoConExito() throws Exception {
        var mockRes = Mockito.mock(EmpleadoResponse.class);

        Mockito.when(authService.register(any())).thenReturn(mockRes);

        mockMvc.perform(
                    post("/auth/registrar")
                            .formField("rfc", "AAAA012345XXX")
                            .formField("nombre", "JUAN")
                            .formField("apellidos", "PEREZ")
                            .formField("correo", "juan.perez@ejemplo.com")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("res"),
                        model().attribute("res", mockRes)
                );
    }

    @Test
    void registrarEmpleadoAdministradorConExito() throws Exception {
        var mockRes = Mockito.mock(EmpleadoResponse.class);

        Mockito.when(authService.register(any())).thenReturn(mockRes);

        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "juan.perez@ejemplo.com")
                                .formField("password", "Qwertyuiop1*")
                                .formField("confirmPassword", "Qwertyuiop1*")
                                .formField("esAdministrador", "true")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("res", "admin"),
                        model().attribute("res", mockRes),
                        model().attribute("admin", true)
                );
    }

    @Test
    void registrarEmpleadoConRfcInvalido() throws Exception {
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "RFC123") // RFC inválido
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "juan.perez@ejemplo.com")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error", "rfcError"),
                        model().attribute("rfc", "RFC123")
                );
    }

    @Test
    void registrarEmpleadoConNombreInvalido() throws Exception {
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "juan123") // Nombre en minúsculas y con números
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "juan.perez@ejemplo.com")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error", "nombreError"),
                        model().attribute("nombre", "juan123")
                );
    }

    @Test
    void registrarEmpleadoConApellidosInvalidos() throws Exception {
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "perez gomez") // Apellidos en minúsculas
                                .formField("correo", "juan.perez@ejemplo.com")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error", "apellidosError"),
                        model().attribute("apellidos", "perez gomez")
                );
    }

    @Test
    void registrarEmpleadoConCorreoInvalido() throws Exception {
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "correo-invalido") // Correo sin formato válido
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error", "correoError"),
                        model().attribute("correo", "correo-invalido")
                );
    }

    @Test
    void registrarEmpleadoAdministradorSinPassword() throws Exception {
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "juan.perez@ejemplo.com")
                                .formField("esAdministrador", "true")
                                // No se proporciona password
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error")
                );
    }

    @Test
    void registrarEmpleadoAdministradorConPasswordInvalida() throws Exception {
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "juan.perez@ejemplo.com")
                                .formField("password", "12345") // Contraseña muy corta y sin complejidad
                                .formField("confirmPassword", "12345")
                                .formField("esAdministrador", "true")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error")
                );
    }

    @Test
    void registrarEmpleadoAdministradorConPasswordsNoCoincidentes() throws Exception {
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "juan.perez@ejemplo.com")
                                .formField("password", "Qwertyuiop1*")
                                .formField("confirmPassword", "Diferente123*") // Contraseñas no coinciden
                                .formField("esAdministrador", "true")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error")
                );
    }

    @Test
    void registrarDosEmpleadosConMismoRfc() throws Exception {
        var mockRes = Mockito.mock(EmpleadoResponse.class);
        
        // Configurar el mock del repositorio para simular que el RFC no existe inicialmente
        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(false);
        Mockito.when(authService.register(any())).thenReturn(mockRes);

        // Primer empleado - registro exitoso
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "juan.perez@ejemplo.com")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("res")
                );

        // Ahora el RFC ya existe en la "base de datos"
        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);

        // Segundo empleado con el mismo RFC - debería fallar por @UniqueRFC
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX") // Mismo RFC
                                .formField("nombre", "MARIA")
                                .formField("apellidos", "LOPEZ")
                                .formField("correo", "maria.lopez@ejemplo.com")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error", "rfcError")
                );
    }

    @Test
    void registrarDosEmpleadosConMismoCorreo() throws Exception {
        var mockRes = Mockito.mock(EmpleadoResponse.class);
        
        // Configurar el mock del repositorio para simular que el correo no existe inicialmente
        Mockito.when(empleadoRepository.existsByCorreo("duplicado@ejemplo.com")).thenReturn(false);
        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(false);
        Mockito.when(authService.register(any())).thenReturn(mockRes);

        // Primer empleado - registro exitoso
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "AAAA012345XXX")
                                .formField("nombre", "JUAN")
                                .formField("apellidos", "PEREZ")
                                .formField("correo", "duplicado@ejemplo.com")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("res")
                );

        // Ahora el correo ya existe en la "base de datos"
        Mockito.when(empleadoRepository.existsByCorreo("duplicado@ejemplo.com")).thenReturn(true);
        Mockito.when(empleadoRepository.existsByRfc("BBBB987654YYY")).thenReturn(false);

        // Segundo empleado con el mismo correo - debería fallar por @UniqueEmail
        mockMvc.perform(
                        post("/auth/registrar")
                                .formField("rfc", "BBBB987654YYY")
                                .formField("nombre", "MARIA")
                                .formField("apellidos", "LOPEZ")
                                .formField("correo", "duplicado@ejemplo.com") // Mismo correo
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("auth/register"),
                        model().attributeExists("error", "correoError")
                );
    }
}