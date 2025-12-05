package mx.uaemex.fi.api.controller;

import mx.uaemex.fi.api.exception.NotFoundException;
import mx.uaemex.fi.api.model.Empleado;
import mx.uaemex.fi.api.model.Nomina;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import mx.uaemex.fi.api.service.CustomUserDetailsService;
import mx.uaemex.fi.api.service.EmpleadoService;
import mx.uaemex.fi.api.service.JwtServiceImp;
import mx.uaemex.fi.api.service.NominaService;
import mx.uaemex.fi.api.validation.PeriodoValidator;
import mx.uaemex.fi.api.validation.RFCExistsValidator;
import mx.uaemex.fi.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class, PeriodoValidator.class, RFCExistsValidator.class})
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmpleadoService empleadoService;

    @MockitoBean
    private NominaService nominaService;

    @MockitoBean
    private JwtServiceImp jwtService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @MockitoBean
    private EmpleadoRepository empleadoRepository;

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void obtenerPaginaDashboard() throws Exception {
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build());

        Mockito.when(empleadoService.obtenerTodos()).thenReturn(empleados);

        mockMvc.perform(get("/admin/dashboard"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/dashboard"),
                        model().attributeExists("admin", "empleados"),
                        model().attribute("admin", "admin@test.com")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void verNominaConRfcValido() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(get("/admin/nomina/consultar")
                        .param("rfc", "AAAA012345XXX"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/ver_nomina"),
                        model().attributeExists("admin", "empleado"),
                        model().attribute("empleado", empleado)
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void verNominaConRfcInexistente() throws Exception {
        Mockito.when(empleadoService.buscarPorRFC(anyString()))
                .thenThrow(new NotFoundException("Empleado no encontrado"));

        mockMvc.perform(get("/admin/nomina/consultar")
                        .param("rfc", "XXXX999999XXX"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/error/notfound")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void verNominaSinParametroRfc() throws Exception {
        mockMvc.perform(get("/admin/nomina/consultar"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/error/notfound")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void obtenerPaginaRegistrarNomina() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(get("/admin/nomina/registrar")
                        .param("rfc", "AAAA012345XXX"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("admin", "empleado"),
                        model().attribute("empleado", empleado)
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void obtenerPaginaRegistrarNominaConRfcInexistente() throws Exception {
        Mockito.when(empleadoService.buscarPorRFC(anyString()))
                .thenThrow(new NotFoundException("Empleado no encontrado"));

        mockMvc.perform(get("/admin/nomina/registrar")
                        .param("rfc", "XXXX999999XXX"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/error/notfound")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void obtenerPaginaRegistrarNominaSinParametroRfc() throws Exception {
        mockMvc.perform(get("/admin/nomina/registrar"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/error/notfound")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConExito() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "10000.50")
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/dashboard")
                );

        Mockito.verify(nominaService).generarNomina(any());
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaSinRfc() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        // El RFC es null, por lo que el servicio puede lanzar error al buscarlo
        Mockito.when(empleadoService.buscarPorRFC(null))
                .thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("salario", "10000.50")
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error", "rfcError")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConRfcInexistente() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("XXXX999999XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("XXXX999999XXX")).thenReturn(false);
        Mockito.when(empleadoService.buscarPorRFC("XXXX999999XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "XXXX999999XXX")
                        .formField("salario", "10000.50")
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error", "rfcError")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaSinSalario() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error", "salarioError")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConSalarioInvalido() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "0.00") // Salario inválido
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error", "salarioError")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConSalarioNegativo() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "-1000.00") // Salario negativo
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error", "salarioError")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaSinFechaInicio() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "10000.50")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error", "fechaInicioError")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaSinFechaFin() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "10000.50")
                        .formField("fechaInicio", "2025-01-01"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error", "fechaFinError")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConFechaInicioMayorQueFechaFin() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "10000.50")
                        .formField("fechaInicio", "2025-01-31") // Fecha inicio posterior
                        .formField("fechaFin", "2025-01-01"))   // Fecha fin anterior
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConFechasIguales() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "10000.50")
                        .formField("fechaInicio", "2025-01-15") // Misma fecha
                        .formField("fechaFin", "2025-01-15"))   // Misma fecha
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void eliminarNominaConExito() throws Exception {
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Nomina nomina = Nomina.builder()
                .id(1)
                .salarioBruto(10000.0f)
                .periodoInicio(LocalDate.of(2025, 1, 1))
                .periodoFin(LocalDate.of(2025, 1, 31))
                .empleado(empleado)
                .build();

        Mockito.when(nominaService.obtenerNomina(1)).thenReturn(nomina);

        mockMvc.perform(post("/admin/nomina/1/eliminar"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/nomina/consultar?rfc=AAAA012345XXX")
                );

        Mockito.verify(nominaService).eliminarNomina(1);
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void eliminarNominaInexistente() throws Exception {
        Mockito.when(nominaService.obtenerNomina(999))
                .thenThrow(new NotFoundException("Nómina no encontrada"));

        mockMvc.perform(post("/admin/nomina/999/eliminar"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/error/notfound")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void obtenerPaginaErrorNotFound() throws Exception {
        mockMvc.perform(get("/admin/error/notfound"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/not_found"),
                        model().attributeExists("admin")
                );
    }

    @Test
    void dashboardSinAutenticacion() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = "USER")
    void dashboardConRolIncorrecto() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void registrarNominaSinAutenticacion() throws Exception {
        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "10000.50")
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = "USER")
    void eliminarNominaConRolIncorrecto() throws Exception {
        mockMvc.perform(post("/admin/nomina/1/eliminar"))
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void obtenerPaginaRegistrarNominaSinRfc() throws Exception {
        mockMvc.perform(get("/admin/nomina/registrar"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/error/notfound")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConSalarioCero() throws Exception {
        var empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "0.00")
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/nomina"),
                        model().attributeExists("error", "salarioError")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConSalarioMinimo() throws Exception {
        var empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        Mockito.when(empleadoRepository.existsByRfc("AAAA012345XXX")).thenReturn(true);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(empleado);

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "AAAA012345XXX")
                        .formField("salario", "0.02")
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/dashboard")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void eliminarNominaPorId() throws Exception {
        var empleado = Empleado.builder()
                .id(1)
                .rfc("AAAA012345XXX")
                .nombre("JUAN")
                .apellidos("PEREZ")
                .correo("juan@test.com")
                .build();

        var nomina = Nomina.builder()
                .id(1)
                .salarioBruto(10000.0f)
                .periodoInicio(LocalDate.of(2025, 1, 1))
                .periodoFin(LocalDate.of(2025, 1, 31))
                .empleado(empleado)
                .build();

        Mockito.when(nominaService.obtenerNomina(1)).thenReturn(nomina);

        mockMvc.perform(post("/admin/nomina/1/eliminar"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/nomina/consultar?rfc=AAAA012345XXX")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void listarEmpleadosVacio() throws Exception {
        Mockito.when(empleadoService.obtenerTodos()).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/admin/dashboard"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/dashboard"),
                        model().attributeExists("empleados", "admin"),
                        model().attribute("empleados", java.util.Collections.emptyList())
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void verEmpleadoConMultiplesNominas() throws Exception {
        var mockEmpleado = Mockito.mock(mx.uaemex.fi.api.model.Empleado.class);
        var nominas = java.util.Arrays.asList(
                Mockito.mock(mx.uaemex.fi.api.model.Nomina.class),
                Mockito.mock(mx.uaemex.fi.api.model.Nomina.class),
                Mockito.mock(mx.uaemex.fi.api.model.Nomina.class)
        );

        Mockito.when(mockEmpleado.getNominas()).thenReturn(nominas);
        Mockito.when(empleadoService.buscarPorRFC("AAAA012345XXX")).thenReturn(mockEmpleado);

        mockMvc.perform(get("/admin/nomina/consultar")
                        .param("rfc", "AAAA012345XXX"))
                .andExpectAll(
                        status().isOk(),
                        view().name("admin/ver_nomina"),
                        model().attributeExists("empleado", "admin")
                );
    }

    @Test
    void obtenerPaginaRegistrarNominaSinAutenticacion() throws Exception {
        mockMvc.perform(get("/admin/nomina/registrar"))
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = "USER")
    void obtenerPaginaRegistrarNominaConRolIncorrecto() throws Exception {
        mockMvc.perform(get("/admin/nomina/registrar"))
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConCamposVacios() throws Exception {
        Mockito.when(empleadoService.buscarPorRFC("")).thenThrow(new NotFoundException("Empleado no encontrado"));

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "")
                        .formField("salario", "")
                        .formField("fechaInicio", "")
                        .formField("fechaFin", ""))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/error/notfound")
                );
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void registrarNominaConRfcFormatoInvalido() throws Exception {
        Mockito.when(empleadoService.buscarPorRFC("RFC-INVALIDO")).thenThrow(new NotFoundException("Empleado no encontrado"));

        mockMvc.perform(post("/admin/nomina/registrar")
                        .formField("rfc", "RFC-INVALIDO")
                        .formField("salario", "10000")
                        .formField("fechaInicio", "2025-01-01")
                        .formField("fechaFin", "2025-01-31"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/admin/error/notfound")
                );
    }
}

