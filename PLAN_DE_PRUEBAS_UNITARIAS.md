# Plan de Pruebas Unitarias
## Sistema de Nómina - UAEMex

---

## 📋 Índice

1. [Resumen Ejecutivo](#resumen-ejecutivo)
2. [Alcance del Plan de Pruebas](#alcance-del-plan-de-pruebas)
3. [Estrategia de Pruebas](#estrategia-de-pruebas)
4. [Pruebas por Capa](#pruebas-por-capa)
5. [Métricas de Cobertura](#métricas-de-cobertura)
6. [Herramientas y Frameworks](#herramientas-y-frameworks)

---

## Resumen Ejecutivo

Este documento describe el plan completo de pruebas unitarias para la aplicación monolítica de gestión de nómina de la UAEMex. El plan cubre todas las capas de la aplicación: modelos, repositorios, servicios, validadores, filtros y controladores.

### Objetivo
Garantizar la calidad y correcto funcionamiento de todos los componentes del sistema mediante pruebas unitarias exhaustivas que cubran casos de éxito, casos de error y casos límite.

### Estado Actual
- ✅ Controladores: 2 archivos de prueba existentes (AdminControllerTest, AuthControllerTest) - **Parcialmente implementados**
- ❌ Servicios: 0 archivos de prueba - **Pendiente**
- ❌ Validadores: 0 archivos de prueba - **Pendiente**
- ❌ Filtros: 0 archivos de prueba - **Pendiente**
- ❌ DTOs: 0 archivos de prueba - **Pendiente**
- ❌ Modelos: 0 archivos de prueba - **Pendiente**

---

## Alcance del Plan de Pruebas

### Componentes a Probar

#### 1. Capa de Modelo (Entities)
- **Empleado.java**
- **Nomina.java**
- **Acceso.java**

#### 2. Capa de Repositorio
- **EmpleadoRepository.java**
- **NominaRepository.java**

#### 3. Capa de Servicio
- **AuthServiceImp.java**
- **EmpleadoServiceImp.java**
- **NominaServiceImp.java**
- **JwtServiceImp.java**
- **CalculoNominaService2025.java**
- **CustomUserDetailsService.java**
- **UserDetailsAdapter.java**

#### 4. Capa de Validación
- **UniqueRFCValidator.java**
- **UniqueEmailValidator.java**
- **RFCExistsValidator.java**
- **PeriodoValidator.java**
- **ConditionalPasswordValidator.java**

#### 5. Capa de Filtros
- **JwtAuthenticationFilter.java**

#### 6. Capa de Controlador
- **AuthController.java** (ampliar pruebas existentes)
- **AdminController.java** (ampliar pruebas existentes)

#### 7. DTOs y Records
- **RegisterRequest.java**
- **LoginRequest.java**
- **NominaRequest.java**
- **JwtResponse.java**
- **EmpleadoResponse.java**

#### 8. Excepciones
- **NotFoundException.java**
- **InvalidCredentialsException.java**

---

## Estrategia de Pruebas

### Principios Generales
1. **Aislamiento**: Cada prueba debe ser independiente usando mocks y stubs
2. **AAA Pattern**: Arrange-Act-Assert en todas las pruebas
3. **Cobertura**: Mínimo 80% de cobertura de líneas de código
4. **Nomenclatura**: `métodoAProbar_condición_resultadoEsperado`
5. **Given-When-Then**: Documentación clara de cada escenario

### Tipos de Pruebas
- ✅ **Pruebas Unitarias**: Componentes individuales aislados
- ✅ **Pruebas de Integración**: Interacción entre capas (limitado)
- ✅ **Pruebas de Validación**: Validaciones de datos de entrada
- ✅ **Pruebas de Seguridad**: Autenticación y autorización

---

## Pruebas por Capa

### 1. CAPA DE MODELO (Entities)

#### 1.1 EmpleadoTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/model/EmpleadoTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `builder_todosLosCampos_creaEmpleadoCorrectamente` | Verifica que el builder de Lombok crea un empleado con todos los campos | Alta |
| 2 | `settersYGetters_todosLosCampos_funcionanCorrectamente` | Valida que todos los getters y setters funcionen | Alta |
| 3 | `relaciones_acceso_seEstableceCorrectamente` | Verifica la relación OneToOne con Acceso | Media |
| 4 | `relaciones_nominas_seEstableceCorrectamente` | Verifica la relación OneToMany con Nóminas | Media |
| 5 | `equals_empleadosConMismoId_sonIguales` | Verifica la igualdad de objetos | Baja |
| 6 | `hashCode_empleadosConMismoId_tienenMismoHash` | Verifica consistencia de hashCode | Baja |
| 7 | `toString_empleado_retornaStringRepresentativo` | Verifica el método toString de Lombok | Baja |

#### 1.2 NominaTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/model/NominaTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `builder_todosLosCampos_creaNominaCorrectamente` | Verifica que el builder crea una nómina correctamente | Alta |
| 2 | `settersYGetters_todosLosCampos_funcionanCorrectamente` | Valida getters y setters | Alta |
| 3 | `relacion_empleado_seAsignaCorrectamente` | Verifica la relación ManyToOne con Empleado | Media |
| 4 | `calculos_salarioBruto_seAlmacenaCorrectamente` | Verifica almacenamiento de cálculos | Media |
| 5 | `periodo_fechas_seAlmacenCorrectamente` | Verifica las fechas de periodo | Media |

#### 1.3 AccesoTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/model/AccesoTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `builder_conEmpleadoYPassword_creaAccesoCorrectamente` | Verifica creación con builder | Alta |
| 2 | `relacion_empleado_seAsignaCorrectamente` | Verifica relación OneToOne con Empleado | Alta |
| 3 | `hashedPassword_seAlmacenaCorrectamente` | Verifica almacenamiento seguro de contraseña | Alta |

---

### 2. CAPA DE REPOSITORIO

#### 2.1 EmpleadoRepositoryTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/repository/EmpleadoRepositoryTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `findByRfc_rfcExistente_retornaEmpleado` | Buscar empleado por RFC existente | Alta |
| 2 | `findByRfc_rfcInexistente_retornaNull` | Buscar empleado por RFC inexistente | Alta |
| 3 | `findByCorreo_correoExistente_retornaListaEmpleados` | Buscar por correo existente | Alta |
| 4 | `findByCorreo_correoInexistente_retornaListaVacia` | Buscar por correo inexistente | Alta |
| 5 | `existsByRfc_rfcExistente_retornaTrue` | Verificar existencia por RFC | Alta |
| 6 | `existsByRfc_rfcInexistente_retornaFalse` | Verificar no existencia por RFC | Alta |
| 7 | `existsByCorreo_correoExistente_retornaTrue` | Verificar existencia por correo | Alta |
| 8 | `existsByCorreo_correoInexistente_retornaFalse` | Verificar no existencia por correo | Alta |
| 9 | `save_empleadoNuevo_guardaCorrectamente` | Guardar nuevo empleado | Alta |
| 10 | `save_empleadoConAcceso_guardaAccesoCascada` | Guardar empleado con acceso (cascade) | Alta |
| 11 | `delete_empleadoExistente_eliminaCorrectamente` | Eliminar empleado | Media |

#### 2.2 NominaRepositoryTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/repository/NominaRepositoryTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `findById_idExistente_retornaNomina` | Buscar nómina por ID existente | Alta |
| 2 | `findById_idInexistente_retornaEmpty` | Buscar nómina por ID inexistente | Alta |
| 3 | `save_nominaNueva_guardaCorrectamente` | Guardar nueva nómina | Alta |
| 4 | `deleteById_idExistente_eliminaCorrectamente` | Eliminar nómina | Alta |
| 5 | `existsById_idExistente_retornaTrue` | Verificar existencia de nómina | Media |

---

### 3. CAPA DE SERVICIO

#### 3.1 AuthServiceImpTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/service/AuthServiceImpTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `login_credencialesValidas_retornaJwtResponse` | Login exitoso con credenciales válidas | Crítica |
| 2 | `login_correoInexistente_lanzaInvalidCredentialsException` | Login con correo no registrado | Crítica |
| 3 | `login_passwordIncorrecta_lanzaInvalidCredentialsException` | Login con contraseña incorrecta | Crítica |
| 4 | `login_empleadoSinAcceso_lanzaInvalidCredentialsException` | Login de empleado sin acceso | Crítica |
| 5 | `register_empleadoNormal_creaEmpleadoSinAcceso` | Registro de empleado normal | Alta |
| 6 | `register_administrador_creaEmpleadoConAcceso` | Registro de administrador | Alta |
| 7 | `register_passwordSeEncripta_usaPasswordEncoder` | Verificar encriptación de contraseña | Alta |
| 8 | `register_datosValidos_retornaEmpleadoResponse` | Registro exitoso retorna DTO | Alta |
| 9 | `register_guardaEnRepositorio_llamaSaveEmpleado` | Verifica persistencia en BD | Alta |

#### 3.2 EmpleadoServiceImpTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/service/EmpleadoServiceImpTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `buscarPorRFC_rfcExistente_retornaEmpleado` | Buscar empleado existente | Alta |
| 2 | `buscarPorRFC_rfcInexistente_lanzaNotFoundException` | Buscar empleado inexistente | Alta |
| 3 | `obtenerTodos_empleadosExisten_retornaLista` | Obtener todos los empleados | Alta |
| 4 | `obtenerTodos_sinEmpleados_retornaListaVacia` | Obtener todos cuando no hay empleados | Media |

#### 3.3 NominaServiceImpTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/service/NominaServiceImpTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `generarNomina_empleadoExistente_creaNomina` | Generar nómina con RFC válido | Crítica |
| 2 | `generarNomina_empleadoInexistente_lanzaNotFoundException` | Generar nómina con RFC inválido | Crítica |
| 3 | `generarNomina_calculosCorrectos_usaCalculoNominaService` | Verifica uso de servicio de cálculo | Alta |
| 4 | `generarNomina_agregaNominaAEmpleado_actualizaRelacion` | Verifica relación bidireccional | Alta |
| 5 | `generarNomina_guardaEmpleado_llamaSaveRepository` | Verifica persistencia | Alta |
| 6 | `eliminarNomina_idExistente_eliminaCorrectamente` | Eliminar nómina existente | Alta |
| 7 | `eliminarNomina_idInexistente_lanzaNotFoundException` | Eliminar nómina inexistente | Alta |
| 8 | `obtenerNomina_idExistente_retornaNomina` | Obtener nómina por ID | Alta |
| 9 | `obtenerNomina_idInexistente_lanzaNotFoundException` | Obtener nómina inexistente | Alta |

#### 3.4 JwtServiceImpTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/service/JwtServiceImpTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `generateToken_rfcValido_generaTokenValido` | Generar token JWT válido | Crítica |
| 2 | `generateToken_tokenContieneSujeto_rfcComoSujeto` | Token contiene RFC como subject | Crítica |
| 3 | `generateToken_tokenContieneFechas_issuedAtYExpiration` | Token contiene fechas válidas | Alta |
| 4 | `validateToken_tokenValido_retornaTrue` | Validar token válido | Crítica |
| 5 | `validateToken_tokenExpirado_retornaFalse` | Validar token expirado | Crítica |
| 6 | `validateToken_tokenMalformado_retornaFalse` | Validar token malformado | Alta |
| 7 | `validateToken_tokenInvalido_retornaFalse` | Validar token con firma inválida | Alta |
| 8 | `getRfcFromToken_tokenValido_retornaRfc` | Extraer RFC del token | Crítica |
| 9 | `getRfcFromToken_tokenInvalido_lanzaExcepcion` | Extraer RFC de token inválido | Alta |
| 10 | `getExpirationTime_retornaValorConfiguracion` | Obtener tiempo de expiración | Media |
| 11 | `getSecretKey_generaClaveDesdeBASE64` | Verificar generación de clave secreta | Media |

#### 3.5 CalculoNominaService2025Test.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/service/CalculoNominaService2025Test.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `calcularCuotaFija_rangoMinimo_retorna0` | Salario 0.01-746.04 → cuota 0.00 | Crítica |
| 2 | `calcularCuotaFija_rango2_retorna14_32` | Salario 746.05-6332.05 → cuota 14.32 | Crítica |
| 3 | `calcularCuotaFija_rango3_retorna371_83` | Salario 6332.06-11128.01 → cuota 371.83 | Crítica |
| 4 | `calcularCuotaFija_rango4_retorna893_63` | Salario 11128.02-12935.82 → cuota 893.63 | Crítica |
| 5 | `calcularCuotaFija_rango5_retorna1182_88` | Salario 12935.83-15487.71 → cuota 1182.88 | Crítica |
| 6 | `calcularCuotaFija_rango6_retorna1640_18` | Salario 15487.72-31236.49 → cuota 1640.18 | Crítica |
| 7 | `calcularCuotaFija_rango7_retorna5004_12` | Salario 31236.50-49233.00 → cuota 5004.12 | Crítica |
| 8 | `calcularCuotaFija_rango8_retorna9236_89` | Salario 49233.01-93993.90 → cuota 9236.89 | Crítica |
| 9 | `calcularCuotaFija_rango9_retorna22665_17` | Salario 93993.91-125325.20 → cuota 22665.17 | Crítica |
| 10 | `calcularCuotaFija_rango10_retorna32691_18` | Salario 125325.21-375975.61 → cuota 32691.18 | Crítica |
| 11 | `calcularCuotaFija_rangoMaximo_retorna117912_32` | Salario > 375975.61 → cuota 117912.32 | Crítica |
| 12 | `calcularCuotaFija_limitesDeRango_calculaCorrectamente` | Probar valores límite de cada rango | Alta |
| 13 | `calcularExcedente_rangoMinimo_calculaCorrectamente` | Excedente para rango mínimo | Crítica |
| 14 | `calcularExcedente_todosLosRangos_calculaCorrectamente` | Excedente para todos los rangos | Crítica |
| 15 | `calcularExcedente_valoresLimite_calculaCorrectamente` | Excedente en valores límite | Alta |
| 16 | `calcularPorcentaje_rangoMinimo_retorna1_92Porciento` | Porcentaje para rango mínimo | Crítica |
| 17 | `calcularPorcentaje_todosLosRangos_retornaPorcentajesCorrecto` | Porcentajes para todos los rangos | Crítica |
| 18 | `calcularPorcentaje_rangoMaximo_retorna35Porciento` | Porcentaje para rango máximo | Crítica |

#### 3.6 CustomUserDetailsServiceTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/service/CustomUserDetailsServiceTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `loadUserByUsername_rfcExistente_retornaUserDetails` | Cargar usuario existente | Crítica |
| 2 | `loadUserByUsername_rfcInexistente_lanzaUsernameNotFoundException` | Cargar usuario inexistente | Crítica |
| 3 | `loadUserByUsername_retornaUserDetailsAdapter` | Verifica tipo de retorno correcto | Alta |

#### 3.7 UserDetailsAdapterTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/service/UserDetailsAdapterTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `getAuthorities_empleadoSinAcceso_retornaRoleUser` | Empleado normal tiene ROLE_USER | Crítica |
| 2 | `getAuthorities_empleadoConAcceso_retornaUserYAdmin` | Admin tiene ROLE_USER y ROLE_ADMIN | Crítica |
| 3 | `getPassword_empleadoConAcceso_retornaHashedPassword` | Obtiene contraseña hasheada | Alta |
| 4 | `getPassword_empleadoSinAcceso_retornaNull` | Empleado sin acceso retorna null | Alta |
| 5 | `getUsername_retornaRfc` | Username es el RFC | Alta |
| 6 | `isAccountNonExpired_siempreRetornaTrue` | Cuenta nunca expira | Media |
| 7 | `isAccountNonLocked_siempreRetornaTrue` | Cuenta nunca bloqueada | Media |
| 8 | `isCredentialsNonExpired_siempreRetornaTrue` | Credenciales no expiran | Media |
| 9 | `isEnabled_siempreRetornaTrue` | Cuenta siempre habilitada | Media |

---

### 4. CAPA DE VALIDACIÓN

#### 4.1 UniqueRFCValidatorTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/validation/UniqueRFCValidatorTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `isValid_rfcNoExiste_retornaTrue` | RFC único es válido | Alta |
| 2 | `isValid_rfcExiste_retornaFalse` | RFC duplicado es inválido | Alta |
| 3 | `isValid_rfcNull_retornaTrue` | RFC null es válido (validado por @NotNull) | Media |
| 4 | `isValid_rfcBlanco_retornaTrue` | RFC en blanco es válido | Media |

#### 4.2 UniqueEmailValidatorTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/validation/UniqueEmailValidatorTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `isValid_correoNoExiste_retornaTrue` | Correo único es válido | Alta |
| 2 | `isValid_correoExiste_retornaFalse` | Correo duplicado es inválido | Alta |
| 3 | `isValid_correoNull_retornaTrue` | Correo null es válido | Media |
| 4 | `isValid_correoBlanco_retornaTrue` | Correo en blanco es válido | Media |

#### 4.3 RFCExistsValidatorTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/validation/RFCExistsValidatorTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `isValid_rfcExiste_retornaTrue` | RFC existente es válido | Alta |
| 2 | `isValid_rfcNoExiste_retornaFalse` | RFC inexistente es inválido | Alta |
| 3 | `isValid_rfcNull_retornaFalse` | RFC null es inválido | Media |

#### 4.4 PeriodoValidatorTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/validation/PeriodoValidatorTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `isValid_fechaInicioAntesDeFechaFin_retornaTrue` | Periodo válido | Alta |
| 2 | `isValid_fechaInicioDespuesDeFechaFin_retornaFalse` | Periodo inválido | Alta |
| 3 | `isValid_fechasIguales_retornaFalse` | Fechas iguales son inválidas | Alta |
| 4 | `isValid_fechaInicioNull_retornaFalse` | Fecha inicio null es inválida | Media |
| 5 | `isValid_fechaFinNull_retornaFalse` | Fecha fin null es inválida | Media |

#### 4.5 ConditionalPasswordValidatorTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/validation/ConditionalPasswordValidatorTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `isValid_noEsAdministrador_retornaTrue` | No valida si no es admin | Alta |
| 2 | `isValid_adminConPasswordValida_retornaTrue` | Password válido para admin | Crítica |
| 3 | `isValid_adminConPasswordCorta_retornaFalse` | Password < 12 caracteres | Crítica |
| 4 | `isValid_adminSinMayuscula_retornaFalse` | Password sin mayúscula | Alta |
| 5 | `isValid_adminSinMinuscula_retornaFalse` | Password sin minúscula | Alta |
| 6 | `isValid_adminSinNumero_retornaFalse` | Password sin número | Alta |
| 7 | `isValid_adminSinCaracterEspecial_retornaFalse` | Password sin carácter especial | Alta |
| 8 | `isValid_adminPasswordNoCoinciden_retornaFalse` | Passwords no coinciden | Crítica |
| 9 | `isValid_adminPasswordNull_retornaFalse` | Password null para admin | Alta |
| 10 | `isValid_adminConfirmPasswordNull_retornaFalse` | Confirm password null | Alta |
| 11 | `isValid_adminPasswordBlanco_retornaFalse` | Password en blanco | Alta |

---

### 5. CAPA DE FILTROS

#### 5.1 JwtAuthenticationFilterTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/filter/JwtAuthenticationFilterTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `shouldNotFilter_rutaAuth_retornaTrue` | No filtra rutas /auth/* | Alta |
| 2 | `shouldNotFilter_recursosEstaticos_retornaTrue` | No filtra CSS, JS, imágenes | Alta |
| 3 | `shouldNotFilter_rutaAdmin_retornaFalse` | Filtra rutas protegidas | Alta |
| 4 | `doFilterInternal_tokenValido_autenticaUsuario` | Token válido autentica | Crítica |
| 5 | `doFilterInternal_sinToken_continuaSinAutenticar` | Sin token no autentica | Crítica |
| 6 | `doFilterInternal_tokenInvalido_redireccionaLogin` | Token inválido redirige | Crítica |
| 7 | `doFilterInternal_tokenExpirado_redireccionaLogin` | Token expirado redirige | Crítica |
| 8 | `doFilterInternal_tokenValido_estableceSecurityContext` | Establece contexto de seguridad | Alta |
| 9 | `getJwtFromCookies_cookieAccessToken_extraeToken` | Extrae token de cookie | Alta |
| 10 | `getJwtFromCookies_sinCookies_retornaNull` | Sin cookies retorna null | Alta |
| 11 | `getJwtFromCookies_cookieDiferente_retornaNull` | Cookie incorrecta retorna null | Media |

---

### 6. CAPA DE CONTROLADOR

#### 6.1 AuthControllerTest.java (Ampliar existentes)
**Ubicación**: `src/test/java/mx/uaemex/fi/api/controller/AuthControllerTest.java`

**Casos de Prueba Adicionales**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `registrar_rfcDuplicado_muestraError` | RFC duplicado muestra error de validación | Alta |
| 2 | `registrar_emailDuplicado_muestraError` | Email duplicado muestra error | Alta |
| 3 | `registrar_formatoRfcInvalido_muestraError` | RFC con formato inválido | Alta |
| 4 | `registrar_formatoNombreInvalido_muestraError` | Nombre con formato inválido | Alta |
| 5 | `registrar_formatoEmailInvalido_muestraError` | Email con formato inválido | Alta |
| 6 | `registrar_adminPasswordDebil_muestraError` | Password débil para admin | Alta |
| 7 | `registrar_adminPasswordsNoCoinciden_muestraError` | Passwords no coinciden | Alta |
| 8 | `registrar_errorInesperado_muestraError` | Manejo de excepciones genéricas | Media |
| 9 | `login_credencialesCorrectas_redireccionaDashboard` | Login exitoso redirige | Crítica |
| 10 | `login_credencialesIncorrectas_muestraError` | Login fallido muestra error | Crítica |
| 11 | `login_estableceCookie_cookieConfiguradaCorrectamente` | Cookie se configura correctamente | Alta |
| 12 | `logout_eliminaCookie_cookieExpirada` | Logout expira cookie | Alta |
| 13 | `loginPage_conTokenError_muestraErrorSesion` | Muestra error de sesión expirada | Media |

#### 6.2 AdminControllerTest.java (Ampliar existentes)
**Ubicación**: `src/test/java/mx/uaemex/fi/api/controller/AdminControllerTest.java`

**Casos de Prueba Adicionales**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `dashboard_usuarioNoAutenticado_redireccionaLogin` | Sin auth redirige a login | Alta |
| 2 | `dashboard_listaVacia_muestraListaVacia` | Lista vacía de empleados | Media |
| 3 | `verNomina_rfcInvalido_redireccionaError` | RFC inválido redirige a error | Alta |
| 4 | `verNomina_rfcSinParametro_redireccionaError` | Sin parámetro RFC | Alta |
| 5 | `nominaRegistrar_sinParametroRfc_redireccionaError` | Sin RFC redirige | Alta |
| 6 | `calcularNomina_datosValidos_redireccionaDashboard` | Cálculo exitoso redirige | Alta |
| 7 | `calcularNomina_salarioInvalido_muestraError` | Salario inválido muestra error | Alta |
| 8 | `calcularNomina_periodoInvalido_muestraError` | Periodo inválido muestra error | Alta |
| 9 | `calcularNomina_rfcNoExiste_muestraError` | RFC inexistente muestra error | Alta |
| 10 | `calcularNomina_errorValidacion_mantieneDatos` | Error mantiene datos en formulario | Media |
| 11 | `eliminarNomina_idValido_eliminaYRedireciona` | Eliminación exitosa | Alta |
| 12 | `eliminarNomina_idInvalido_redireccionaError` | ID inválido muestra error | Alta |
| 13 | `handleNotFoundException_lanzaExcepcion_redireccionaError` | Manejo de NotFoundException | Alta |

---

### 7. DTOs Y RECORDS

#### 7.1 RegisterRequestTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/dto/RegisterRequestTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `validaciones_rfcNull_fallaValidacion` | RFC null falla @NotNull | Alta |
| 2 | `validaciones_rfcFormatoInvalido_fallaPattern` | RFC formato inválido | Alta |
| 3 | `validaciones_nombreNull_fallaValidacion` | Nombre null falla | Alta |
| 4 | `validaciones_nombreFormatoInvalido_fallaPattern` | Nombre formato inválido | Alta |
| 5 | `validaciones_apellidosNull_fallaValidacion` | Apellidos null falla | Alta |
| 6 | `validaciones_correoNull_fallaValidacion` | Correo null falla | Alta |
| 7 | `validaciones_correoFormatoInvalido_fallaEmail` | Email formato inválido | Alta |
| 8 | `record_todosLosCampos_seCreaCorrectamente` | Record se crea correctamente | Media |

#### 7.2 LoginRequestTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/dto/LoginRequestTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `record_correoYPassword_seCreaCorrectamente` | Record se crea con ambos campos | Media |
| 2 | `record_camposOpcionales_permitenNull` | Campos permiten null | Baja |

#### 7.3 NominaRequestTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/dto/NominaRequestTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `validaciones_rfcNull_fallaValidacion` | RFC null falla | Alta |
| 2 | `validaciones_salarioNull_fallaValidacion` | Salario null falla | Alta |
| 3 | `validaciones_salarioMenorMinimo_fallaDecimalMin` | Salario < 0.01 falla | Alta |
| 4 | `validaciones_fechaInicioNull_fallaValidacion` | Fecha inicio null falla | Alta |
| 5 | `validaciones_fechaFinNull_fallaValidacion` | Fecha fin null falla | Alta |
| 6 | `validaciones_periodoInvalido_fallaPeriodo` | Periodo inválido falla | Alta |

#### 7.4 JwtResponseTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/dto/JwtResponseTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `record_todosLosCampos_seCreaCorrectamente` | Record se crea correctamente | Media |
| 2 | `record_tokenType_siempreBearer` | Token type es Bearer | Baja |

#### 7.5 EmpleadoResponseTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/dto/EmpleadoResponseTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `record_todosLosCampos_seCreaCorrectamente` | Record se crea correctamente | Media |
| 2 | `record_noIncluyePassword_seguridadDatos` | No expone información sensible | Media |

---

### 8. EXCEPCIONES

#### 8.1 NotFoundExceptionTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/exception/NotFoundExceptionTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `constructor_conMensaje_creaExcepcionConMensaje` | Constructor con mensaje | Media |
| 2 | `getMessage_retornaMensajeOriginal` | Mensaje se preserva | Media |
| 3 | `extendsRuntimeException_esExcepcionNoCheckeada` | Es RuntimeException | Baja |

#### 8.2 InvalidCredentialsExceptionTest.java
**Ubicación**: `src/test/java/mx/uaemex/fi/api/exception/InvalidCredentialsExceptionTest.java`

**Casos de Prueba**:

| # | Nombre del Test | Descripción | Prioridad |
|---|-----------------|-------------|-----------|
| 1 | `constructor_conMensaje_creaExcepcionConMensaje` | Constructor con mensaje | Media |
| 2 | `getMessage_retornaMensajeOriginal` | Mensaje se preserva | Media |
| 3 | `extendsRuntimeException_esExcepcionNoCheckeada` | Es RuntimeException | Baja |

---

## Métricas de Cobertura

### Objetivos de Cobertura por Capa

| Capa | Cobertura Líneas | Cobertura Ramas | Cobertura Métodos |
|------|------------------|-----------------|-------------------|
| **Modelos** | 90% | 85% | 95% |
| **Repositorios** | 85% | 80% | 90% |
| **Servicios** | 90% | 85% | 95% |
| **Validadores** | 95% | 90% | 100% |
| **Filtros** | 85% | 80% | 90% |
| **Controladores** | 85% | 80% | 90% |
| **DTOs** | 80% | 75% | 85% |
| **Excepciones** | 90% | N/A | 100% |

### Cobertura Global Objetivo
- **Líneas de código**: Mínimo 85%
- **Ramas**: Mínimo 80%
- **Métodos**: Mínimo 90%

---

## Herramientas y Frameworks

### Frameworks de Pruebas
- **JUnit 5** (Jupiter): Framework principal de pruebas
- **Mockito**: Mocking y stubbing
- **MockMvc**: Pruebas de controladores
- **Spring Boot Test**: Integración con Spring
- **AssertJ**: Assertions fluidas (opcional)

### Herramientas de Cobertura
- **JaCoCo**: Análisis de cobertura de código
- **SonarQube**: Análisis de calidad (opcional)

### Dependencias Maven

```xml
<dependencies>
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Mockito -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Spring Boot Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Spring Security Test -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- H2 Database (para pruebas de repositorio) -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <!-- JaCoCo Plugin -->
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.11</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

---

## Convenciones de Nomenclatura

### Nombres de Clases de Prueba
- Formato: `{ClaseAProbar}Test.java`
- Ejemplo: `EmpleadoServiceImpTest.java`

### Nombres de Métodos de Prueba
- Formato: `{método}_{condición}_{resultadoEsperado}`
- Ejemplos:
  - `login_credencialesValidas_retornaJwtResponse`
  - `calcularCuotaFija_rangoMinimo_retorna0`
  - `findByRfc_rfcInexistente_retornaNull`

### Estructura de Prueba (AAA Pattern)
```java
@Test
void metodo_condicion_resultado() {
    // Arrange (Preparar)
    // Configurar datos de prueba y mocks
    
    // Act (Actuar)
    // Ejecutar el método a probar
    
    // Assert (Afirmar)
    // Verificar los resultados
}
```

---

## Priorización de Implementación

### Fase 1 - Crítica (Semanas 1-2)
1. **Servicios Críticos**: AuthServiceImp, JwtServiceImp, CalculoNominaService2025
2. **Validadores de Seguridad**: ConditionalPasswordValidator
3. **Filtros de Seguridad**: JwtAuthenticationFilter

### Fase 2 - Alta Prioridad (Semanas 3-4)
1. **Servicios de Negocio**: NominaServiceImp, EmpleadoServiceImp
2. **Validadores de Negocio**: UniqueRFCValidator, UniqueEmailValidator, RFCExistsValidator, PeriodoValidator
3. **Repositorios**: EmpleadoRepository, NominaRepository

### Fase 3 - Media Prioridad (Semana 5)
1. **Controladores**: Completar AuthController y AdminController
2. **Adaptadores**: UserDetailsAdapter, CustomUserDetailsService
3. **DTOs**: Todos los DTOs con validaciones

### Fase 4 - Baja Prioridad (Semana 6)
1. **Modelos**: Empleado, Nomina, Acceso
2. **Excepciones**: NotFoundException, InvalidCredentialsException
3. **DTOs Simples**: JwtResponse, EmpleadoResponse

---

## Indicadores de Éxito

### Criterios de Aceptación
- ✅ Todas las clases tienen su archivo de prueba correspondiente
- ✅ Cobertura de líneas >= 85%
- ✅ Cobertura de ramas >= 80%
- ✅ Cobertura de métodos >= 90%
- ✅ Todas las pruebas pasan exitosamente
- ✅ No hay código duplicado en las pruebas
- ✅ Todas las pruebas están documentadas
- ✅ Se siguen las convenciones de nomenclatura

### Métricas de Calidad
- **Tiempo de ejecución total**: < 30 segundos
- **Pruebas frágiles**: 0 (no deben fallar aleatoriamente)
- **Pruebas independientes**: 100% (sin dependencias entre pruebas)
- **Uso de mocks apropiado**: >= 95% de pruebas unitarias usan mocks

---

## Notas Adicionales

### Consideraciones Especiales

1. **Pruebas de CalculoNominaService2025**
   - Son críticas porque implementan cálculos fiscales oficiales del SAT
   - Deben probar todos los rangos de la tabla de ISR 2025
   - Considerar pruebas parametrizadas para los 11 rangos

2. **Pruebas de Seguridad**
   - JwtServiceImp requiere configuración de secreto y tiempo de expiración
   - JwtAuthenticationFilter necesita mocks de HttpServletRequest/Response
   - Probar escenarios de token expirado, inválido, y malformado

3. **Pruebas de Repositorio**
   - Usar @DataJpaTest para pruebas de repositorio
   - Usar base de datos en memoria H2
   - Verificar cascadas y relaciones bidireccionales

4. **Pruebas de Validadores**
   - Usar ValidatorFactory para pruebas de validación
   - Probar integración con ConstraintValidatorContext
   - Verificar mensajes de error personalizados

5. **Pruebas de Controladores**
   - Usar @WebMvcTest para aislar capa web
   - Mockear servicios y repositorios
   - Probar autorización y autenticación con @WithMockUser

---

## Mantenimiento del Plan

### Actualización del Plan
Este plan debe actualizarse cuando:
- Se agreguen nuevas clases al sistema
- Se modifiquen clases existentes significativamente
- Se identifiquen casos de prueba adicionales
- Se actualicen las dependencias o frameworks

### Revisión Periódica
- **Semanal**: Revisar progreso de implementación
- **Mensual**: Revisar métricas de cobertura
- **Trimestral**: Revisar y actualizar prioridades

---

## Referencias

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [Tablas ISR 2025 - SAT](https://www.sat.gob.mx/)

---

## Resumen de Archivos a Crear

### Total de Archivos de Prueba

| Categoría | Archivos Nuevos | Archivos a Ampliar | Total |
|-----------|-----------------|-------------------|-------|
| Modelos | 3 | 0 | 3 |
| Repositorios | 2 | 0 | 2 |
| Servicios | 7 | 0 | 7 |
| Validadores | 5 | 0 | 5 |
| Filtros | 1 | 0 | 1 |
| Controladores | 0 | 2 | 2 |
| DTOs | 5 | 0 | 5 |
| Excepciones | 2 | 0 | 2 |
| **TOTAL** | **25** | **2** | **27** |

### Total de Casos de Prueba

| Categoría | Casos de Prueba |
|-----------|-----------------|
| Modelos | 17 |
| Repositorios | 16 |
| Servicios | 68 |
| Validadores | 26 |
| Filtros | 11 |
| Controladores | 26 |
| DTOs | 14 |
| Excepciones | 6 |
| **TOTAL** | **184** |

---

## Checklist de Implementación

### Preparación
- [ ] Configurar JaCoCo en pom.xml
- [ ] Configurar H2 para pruebas
- [ ] Crear estructura de carpetas de prueba
- [ ] Definir utilidades y helpers comunes

### Implementación por Fases
#### Fase 1 - Crítica
- [ ] AuthServiceImpTest (9 casos)
- [ ] JwtServiceImpTest (11 casos)
- [ ] CalculoNominaService2025Test (18 casos)
- [ ] ConditionalPasswordValidatorTest (11 casos)
- [ ] JwtAuthenticationFilterTest (11 casos)

#### Fase 2 - Alta
- [ ] NominaServiceImpTest (9 casos)
- [ ] EmpleadoServiceImpTest (4 casos)
- [ ] UniqueRFCValidatorTest (4 casos)
- [ ] UniqueEmailValidatorTest (4 casos)
- [ ] RFCExistsValidatorTest (3 casos)
- [ ] PeriodoValidatorTest (5 casos)
- [ ] EmpleadoRepositoryTest (11 casos)
- [ ] NominaRepositoryTest (5 casos)

#### Fase 3 - Media
- [ ] Ampliar AuthControllerTest (13 casos adicionales)
- [ ] Ampliar AdminControllerTest (13 casos adicionales)
- [ ] UserDetailsAdapterTest (9 casos)
- [ ] CustomUserDetailsServiceTest (3 casos)
- [ ] RegisterRequestTest (8 casos)
- [ ] NominaRequestTest (6 casos)

#### Fase 4 - Baja
- [ ] EmpleadoTest (7 casos)
- [ ] NominaTest (5 casos)
- [ ] AccesoTest (3 casos)
- [ ] NotFoundExceptionTest (3 casos)
- [ ] InvalidCredentialsExceptionTest (3 casos)
- [ ] LoginRequestTest (2 casos)
- [ ] JwtResponseTest (2 casos)
- [ ] EmpleadoResponseTest (2 casos)

### Verificación Final
- [ ] Ejecutar todas las pruebas: `mvn test`
- [ ] Generar reporte de cobertura: `mvn jacoco:report`
- [ ] Verificar cobertura >= 85%
- [ ] Revisar pruebas frágiles o dependientes
- [ ] Documentar resultados

---

**Documento generado el**: 4 de diciembre de 2025  
**Versión**: 1.0  
**Autor**: Sistema de Análisis de Código  
**Estado**: Pendiente de Aprobación

