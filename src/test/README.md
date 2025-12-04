# Guía de Pruebas Unitarias

## Inicio Rápido

### Ejecutar todas las pruebas
```powershell
mvn test
```

### Ejecutar una clase de prueba específica
```powershell
mvn test -Dtest=EmpleadoServiceImpTest
```

### Ejecutar un método de prueba específico
```powershell
mvn test -Dtest=EmpleadoServiceImpTest#buscarPorRFC_rfcExistente_retornaEmpleado
```

### Generar reporte de cobertura
```powershell
mvn clean test jacoco:report
```

El reporte se genera en: `target/site/jacoco/index.html`

### Ver reporte de cobertura
```powershell
start target/site/jacoco/index.html
```

## Estructura de Pruebas

```
src/test/java/mx/uaemex/fi/api/
├── controller/          # Pruebas de controladores
│   ├── AdminControllerTest.java
│   └── AuthControllerTest.java
├── service/             # Pruebas de servicios
│   ├── AuthServiceImpTest.java
│   ├── EmpleadoServiceImpTest.java
│   ├── NominaServiceImpTest.java
│   ├── JwtServiceImpTest.java
│   ├── CalculoNominaService2025Test.java
│   ├── CustomUserDetailsServiceTest.java
│   └── UserDetailsAdapterTest.java
├── repository/          # Pruebas de repositorios
│   ├── EmpleadoRepositoryTest.java
│   └── NominaRepositoryTest.java
├── validation/          # Pruebas de validadores
│   ├── UniqueRFCValidatorTest.java
│   ├── UniqueEmailValidatorTest.java
│   ├── RFCExistsValidatorTest.java
│   ├── PeriodoValidatorTest.java
│   └── ConditionalPasswordValidatorTest.java
├── filter/              # Pruebas de filtros
│   └── JwtAuthenticationFilterTest.java
├── model/               # Pruebas de modelos
│   ├── EmpleadoTest.java
│   ├── NominaTest.java
│   └── AccesoTest.java
├── dto/                 # Pruebas de DTOs
│   ├── RegisterRequestTest.java
│   ├── LoginRequestTest.java
│   ├── NominaRequestTest.java
│   ├── JwtResponseTest.java
│   └── EmpleadoResponseTest.java
├── exception/           # Pruebas de excepciones
│   ├── NotFoundExceptionTest.java
│   └── InvalidCredentialsExceptionTest.java
└── util/                # Utilidades de prueba
    ├── TestDataBuilder.java
    └── TestAssertions.java
```

## Utilidades de Prueba

### TestDataBuilder
Proporciona métodos factory para crear datos de prueba:

```java
// Crear un empleado de prueba
Empleado empleado = TestDataBuilder.crearEmpleado();

// Crear un empleado administrador
Empleado admin = TestDataBuilder.crearEmpleadoAdministrador();

// Crear una nómina
Nomina nomina = TestDataBuilder.crearNomina(empleado, 5000f);

// Crear lista de empleados
List<Empleado> empleados = TestDataBuilder.crearListaEmpleados();
```

### TestAssertions
Proporciona métodos de validación personalizados:

```java
// Verificar empleado completo
TestAssertions.assertEmpleadoEquals(expected, actual);

// Verificar nómina completa
TestAssertions.assertNominaEquals(expected, actual);

// Verificar RFC válido
TestAssertions.assertValidRFC(rfc);

// Verificar si es administrador
TestAssertions.assertEmpleadoEsAdministrador(empleado);
```

## Cobertura Actual

Para ver la cobertura actual, ejecuta:
```powershell
mvn clean test jacoco:report
start target/site/jacoco/index.html
```

### Objetivos de Cobertura
- **Líneas**: 85%
- **Ramas**: 80%
- **Métodos**: 90%

## Convenciones

### Nomenclatura de Pruebas
Formato: `{método}_{condición}_{resultadoEsperado}`

Ejemplos:
- `login_credencialesValidas_retornaJwtResponse`
- `calcularCuotaFija_rangoMinimo_retorna0`
- `findByRfc_rfcInexistente_retornaNull`

### Estructura AAA
Todas las pruebas siguen el patrón Arrange-Act-Assert:

```java
@Test
void metodo_condicion_resultado() {
    // Arrange: Preparar datos y mocks
    Empleado empleado = TestDataBuilder.crearEmpleado();
    when(repository.findByRfc(anyString())).thenReturn(empleado);
    
    // Act: Ejecutar el método a probar
    Empleado resultado = service.buscarPorRFC(TEST_RFC);
    
    // Assert: Verificar resultados
    assertNotNull(resultado);
    assertEquals(TEST_RFC, resultado.getRfc());
}
```

## Tipos de Pruebas

### Pruebas de Repositorio (@DataJpaTest)
```java
@DataJpaTest
class EmpleadoRepositoryTest {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Test
    void testMethod() {
        // ...
    }
}
```

### Pruebas de Servicio
```java
@ExtendWith(MockitoExtension.class)
class EmpleadoServiceImpTest {
    @Mock
    private EmpleadoRepository empleadoRepository;
    
    @InjectMocks
    private EmpleadoServiceImp empleadoService;
    
    @Test
    void testMethod() {
        // ...
    }
}
```

### Pruebas de Controlador (@WebMvcTest)
```java
@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class})
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private EmpleadoService empleadoService;
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void testMethod() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
               .andExpect(status().isOk());
    }
}
```

## Fase de Implementación Actual

### Preparación (Completada)
- [x] Configurar JaCoCo en pom.xml
- [x] Configurar H2 para pruebas
- [x] Crear estructura de carpetas de prueba
- [x] Definir utilidades y helpers comunes
- [x] Verificar pruebas existentes

### Fase 1 - Crítica (En Progreso)
- [ ] AuthServiceImpTest (9 casos)
- [ ] JwtServiceImpTest (11 casos)
- [ ] CalculoNominaService2025Test (18 casos)
- [ ] ConditionalPasswordValidatorTest (11 casos)
- [ ] JwtAuthenticationFilterTest (11 casos)

## Recursos

- [Plan de Pruebas Completo](../../../PLAN_DE_PRUEBAS_UNITARIAS.md)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)

## Troubleshooting

### Las pruebas no encuentran las clases
```powershell
mvn clean compile test-compile
```

### JaCoCo no genera el reporte
```powershell
mvn clean test jacoco:report
```

### Problemas con Maven
Asegúrate de usar Maven desde IntelliJ:
```powershell
& "C:\Users\DELL\AppData\Local\Programs\IntelliJ IDEA Ultimate\plugins\maven\lib\maven3\bin\mvn.cmd" test
```
