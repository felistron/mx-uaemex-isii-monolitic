# 📘 Manual de Usuario
## Sistema de Gestión de Nómina UAEMex

---

### Bienvenido

Este manual le guiará paso a paso en el uso del **Sistema de Gestión de Nómina** de la Universidad Autónoma del Estado de México. El sistema le permitirá registrar empleados, calcular nóminas y consultar información de manera fácil y segura.

**¿Qué puedo hacer con este sistema?**
- ✅ Registrar nuevos empleados
- ✅ Iniciar sesión como administrador
- ✅ Consultar la lista de empleados
- ✅ Calcular nóminas automáticamente
- ✅ Ver el historial de nóminas de cada empleado
- ✅ Eliminar nóminas si es necesario

---

## Índice

1. [Requisitos Previos](#requisitos-previos)
2. [Acceso al Sistema](#acceso-al-sistema)
3. [Registrar un Nuevo Empleado](#registrar-un-nuevo-empleado)
4. [Iniciar Sesión como Administrador](#iniciar-sesión-como-administrador)
5. [Panel de Administración (Dashboard)](#panel-de-administración-dashboard)
6. [Registrar una Nómina](#registrar-una-nómina)
7. [Consultar Nóminas de un Empleado](#consultar-nóminas-de-un-empleado)
8. [Eliminar una Nómina](#eliminar-una-nómina)
9. [Cerrar Sesión](#cerrar-sesión)
10. [Preguntas Frecuentes](#preguntas-frecuentes)
11. [Solución de Problemas](#solución-de-problemas)
12. [Glosario de Términos](#glosario-de-términos)

---

## 1. Requisitos Previos

### ¿Qué necesito para usar el sistema?

**Requisitos básicos:**
- ✅ Una computadora con acceso a internet
- ✅ Un navegador web actualizado (recomendamos Google Chrome o Microsoft Edge)
- ✅ La dirección web (URL) del sistema proporcionada por el área de TI

**Navegadores compatibles:**
- ✅ Google Chrome (versión 100 o superior)
- ✅ Microsoft Edge (versión 100 o superior)
- ✅ Mozilla Firefox (versión 100 o superior)
- ✅ Safari (versión 15 o superior)

⚠️ **Nota:** Por seguridad, el sistema requiere JavaScript habilitado en su navegador.

---

## 2. Acceso al Sistema

### ¿Cómo entro al sistema?

**Paso 1:** Abra su navegador web preferido

**Paso 2:** En la barra de direcciones, escriba la URL proporcionada por TI:
```
http://[servidor]:8080/auth/login
```

**Paso 3:** Presione la tecla `Enter`

**¿Qué veo ahora?**
Aparecerá la pantalla de inicio de sesión del sistema.

![](/docs/images/auth-login-01.jpeg)

---

## 3. Registrar un Nuevo Empleado

### ¿Cómo registro a un empleado en el sistema?

Existen dos tipos de empleados que puede registrar:
- **Empleado Regular:** Puede ver su propia información (función futura)
- **Administrador:** Puede gestionar nóminas de todos los empleados

### Pasos para Registrar un Empleado Regular

**Paso 1:** En la pantalla de inicio de sesión, haga clic en el enlace **"Registrar empleado"**

**Paso 2:** Complete el formulario con los siguientes datos:

#### 📝 Campos del Formulario de Registro

| Campo                  | Descripción                                     | Ejemplo                 | ¿Es Obligatorio? |
|------------------------|-------------------------------------------------|-------------------------|------------------|
| **RFC**                | Registro Federal de Contribuyentes del empleado | `CABA800101ABC`         | ✅ Sí             |
| **Nombre**             | Nombre(s) del empleado                          | `JUAN CARLOS`           | ✅ Sí             |
| **Apellidos**          | Apellidos del empleado                          | `GARCÍA LÓPEZ`          | ✅ Sí             |
| **Correo electrónico** | Email del empleado                              | `juan.garcia@uaemex.mx` | ✅ Sí             |

![](/docs/images/auth-registrar-01.jpeg)

**Paso 3:** Haga clic en el botón **"Registrar"**

✅ **¡Listo!** Si todo fue correcto, verá un mensaje de confirmación:
```
Se ha registrado el empleado juan.garcia@uaemex.mx exitosamente.
```

### Pasos para Registrar un Administrador

**Paso 1 a 2:** Igual que el registro de empleado regular

**Paso 3:** **Active la casilla** ☑️ **"Crear como administrador"**

**Paso 4:** Aparecerán campos adicionales para la contraseña:

![](/docs/images/auth-registrar-02.jpeg)

**Paso 5:** Complete los campos de contraseña:

| Campo                    | Descripción                                | ¿Es Obligatorio?       |
|--------------------------|--------------------------------------------|------------------------|
| **Contraseña**           | Contraseña de acceso para el administrador | ✅ Sí (solo para admin) |
| **Confirmar contraseña** | Repita la contraseña para verificar        | ✅ Sí (solo para admin) |

⚠️ **Requisitos de la Contraseña para Administradores:**
- Mínimo 12 caracteres
- Al menos una letra MAYÚSCULA
- Al menos una letra minúscula
- Al menos un número (0-9)
- Al menos un carácter especial (!, @, #, $, %, etc.)

📌 **Ejemplo de contraseña válida:** `Admin2025!Seguro`

**Paso 6:** Haga clic en el botón **"Registrar"**

✅ **¡Listo!** Si todo fue correcto, verá un mensaje de confirmación:
```
Se ha registrado el empleado juan.garcia@uaemex.mx exitosamente.
Haz clic aquí para iniciar sesion como administrador.
```

---

## 4. Iniciar Sesión como Administrador

### ¿Cómo ingreso al panel de administración?

Solo los usuarios registrados como **administradores** pueden gestionar nóminas.

**Paso 1:** En la pantalla de inicio de sesión, ingrese sus credenciales:

![](/docs/images/auth-login-02.jpeg)

| Campo                  | Descripción                      | Ejemplo                 |
|------------------------|----------------------------------|-------------------------|
| **Correo electrónico** | El correo con el que se registró | `juan.garcia@uaemex.mx` |
| **Contraseña**         | La contraseña que estableció     | `Admin2025!Seguro`      |

**Paso 2:** Haga clic en el botón **"Iniciar Sesión"**

✅ **¡Correcto!** Si sus credenciales son válidas, será redirigido automáticamente al **Panel de Administración**.

❌ **Error:** Si aparece el mensaje "Credenciales incorrectas":
- Verifique que escribió correctamente su correo
- Verifique que escribió correctamente su contraseña (distingue mayúsculas/minúsculas)
- Asegúrese de que su cuenta fue registrada como administrador

---

## 5. Panel de Administración (Dashboard)

### 📊 ¿Qué puedo hacer en el panel de administración?

Al iniciar sesión correctamente, verá el **Dashboard** o panel principal:

![](/docs/images/admin-dashboard-01.jpeg)

### 🔍 Elementos del Dashboard

| Elemento                 | Descripción                                               | ¿Para qué sirve?                          |
|--------------------------|-----------------------------------------------------------|-------------------------------------------|
| **RFC**                  | Muestra su RFC                                            | Saber con qué cuenta está conectado       |
| **[Cerrar Sesión]**      | Botón para salir del sistema                              | Cerrar su sesión de forma segura          |
| **Empleados**            | Tabla con todos los empleados                             | Ver todos los empleados registrados       |
| **[Registrar empleado]** | Botón para registrar nuevo empleado                       | Registrar empleado                        |
| **RFC**                  | Columna con el RFC del empleado                           | Identificar al empleado                   |
| **Nombre(s)**            | Columna con nombre                                        | Ver el nombre del empleado                |
| **Apellido(s)**          | Columna con apellidos                                     | Ver el apellido del empleado              |
| **Correo**               | Columna con correo electrónico                            | Ver el correo del empleado                |
| **Administrador**        | Columna que dicta si el empleado es o no administrador    | Ver si el empleado es administrador       |
| **Acciones**             | Columna que contiene diferentes acciones para un empleado | Ejecutar una acción sobre un empleado     |
| **[Calcular nómina]**    | Botón para calcular nueva nómina                          | Generar una nueva nómina para el empleado |
| **[Consultar nóminas]**  | Botón para consultar todas las nóminas                    | Consultar nóminas del empleado            |

---

## 6. Registrar una Nómina

### ¿Cómo cálculo y registro una nómina para un empleado?

El sistema calcula automáticamente los impuestos según las **tablas del SAT 2025**.

**Paso 1:** En el Dashboard, localice al empleado en la lista

**Paso 2:** Haga clic en el botón **[Calcular nómina]** (más) en la columna "Acciones"

**Paso 3:** Se abrirá el formulario de registro de nómina:

![](/docs/images/admin-nomina-registrar-01.jpeg)

**Paso 4:** Complete los campos del formulario:

| Campo               | Descripción                        | Ejemplo      | ¿Es Obligatorio? |
|---------------------|------------------------------------|--------------|------------------|
| **Salario Bruto**   | Salario mensual antes de impuestos | `15000.00`   | ✅ Sí             |
| **Fecha de inicio** | Primer día del período de nómina   | `01/12/2025` | ✅ Sí             |
| **Fecha de fin**    | Último día del período de nómina   | `15/12/2025` | ✅ Sí             |

📌 **Importante:**
- El salario debe ser mayor a 0.01 pesos
- La fecha de inicio debe ser anterior a la fecha de fin
- Las fechas deben estar en formato DD/MM/AAAA

**Paso 5:** Haga clic en **"Guardar"**

✅ **¡Procesando!** El sistema calculará automáticamente:
- Límite inferior del rango salarial
- Excedente sobre el límite inferior
- Cuota fija de ISR
- Porcentaje sobre excedente
- ISR total a retener

**Paso 6:** Será redirigido al Dashboard.

---

## 7. Consultar Nóminas de un Empleado

### ¿Cómo veo el historial de nóminas de un empleado?

**Paso 1:** En el Dashboard, localice al empleado en la lista

**Paso 2:** Haga clic en el botón **[Consultar nóminas]** en la columna "Acciones"

**Paso 3:** Se mostrará el detalle del empleado y su historial de nóminas:

![](/docs/images/admin-nomina-consultar-01.jpeg)

### Información Mostrada

| Columna             | Descripción                                        |
|---------------------|----------------------------------------------------|
| **Período**         | Fechas de inicio y fin de la nómina                |
| **Salario**         | Salario bruto del empleado                         |
| **Excedente**       | Excedente sobre limite inferior del rango salarial |
| **Porcentaje**      | Porcentaje sobre excedente                         |
| **Cuota fija**      | Cuota fija aplicado al rango salarial              |
| **Retención total** | Impuesto calculado automáticamente                 |
| **Salario neto**    | Salario considerando las retenciones               |
| **Acciones**        | Opciones disponibles (Eliminar)                    |

---

## 8. Eliminar una Nómina

### ¿Cómo elimino una nómina registrada por error?

⚠️ **Advertencia:** Esta acción **NO** se puede deshacer. Asegúrese de que realmente desea eliminar la nómina.

**Paso 1:** Siga los pasos de [Consultar Nóminas](#7-consultar-nóminas-de-un-empleado) para ver el historial

**Paso 2:** Localice la nómina que desea eliminar en la tabla

**Paso 3:** Haga clic en el botón **[Eliminar]** de la nómina correspondiente

**Paso 4:** El sistema eliminará la nómina y actualizará la lista automáticamente

### Casos de Uso para Eliminar Nóminas

**Cuándo eliminar:**
- ✅ Se registró una nómina con datos incorrectos
- ✅ Se duplicó una nómina por error
- ✅ El período de nómina fue incorrecto
- ✅ El salario ingresado fue incorrecto

**Qué hacer después de eliminar:**
1. Verifique que la nómina ya no aparece en la lista
2. Si es necesario, registre nuevamente la nómina con los datos correctos
3. Documente el motivo de la eliminación en sus registros internos

---

## 9. Cerrar Sesión

### ¿Cómo salgo del sistema de forma segura?

Es **muy importante** cerrar sesión cuando termine de usar el sistema, especialmente si está en una computadora compartida.

**Paso 1:** En cualquier pantalla del panel de administración, localice el botón **"Cerrar Sesión"** en la esquina superior derecha.

![](/docs/images/admin-dashboard-01.jpeg)

**Paso 2:** Haga clic en **"Cerrar Sesión"**

**Paso 3:** Será redirigido automáticamente a la pantalla de inicio de sesión.

✅ **¡Listo!** Su sesión ha sido cerrada de forma segura.

### Buenas Prácticas de Seguridad

| Acción                                          | ¿Por qué es importante?                        |
|-------------------------------------------------|------------------------------------------------|
| Cerrar sesión al terminar                       | Evita que otras personas accedan con su cuenta |
| No compartir contraseñas                        | Cada administrador debe tener su propia cuenta |
| Cerrar el navegador                             | Proporciona una capa adicional de seguridad    |
| No guardar contraseñas en computadoras públicas | Protege su información personal                |

---

## 10. Preguntas Frecuentes

### ❓ Preguntas Comunes

<details>
<summary><strong>¿Puedo registrar a un empleado que ya existe?</strong></summary>

**Respuesta:** No. El sistema no permite registrar empleados con el mismo RFC o correo electrónico dos veces. Si intenta hacerlo, recibirá un mensaje de error:
```
❌ Error: El RFC ya se encuentra registrado
```
o
```
❌ Error: El correo ya se encuentra registrado
```
</details>

<details>
<summary><strong>¿Qué hago si olvidé mi contraseña de administrador?</strong></summary>

**Respuesta:** Actualmente, debe contactar al área de Tecnologías de la Información (TI) para que restablezcan su contraseña. **No intente** crear una nueva cuenta con el mismo correo, ya que el sistema no lo permitirá.
</details>

<details>
<summary><strong>¿Puedo modificar una nómina ya registrada?</strong></summary>

**Respuesta:** No directamente. Si una nómina tiene datos incorrectos, debe:
1. Eliminar la nómina incorrecta
2. Registrar nuevamente la nómina con los datos correctos
</details>

<details>
<summary><strong>¿Cómo sé si el cálculo de ISR es correcto?</strong></summary>

**Respuesta:** El sistema utiliza las **tablas oficiales del SAT para el año 2025**. Los cálculos son automáticos y precisos. El sistema considera:
- 11 rangos de salario
- Cuotas fijas por rango
- Porcentajes sobre excedente de límite inferior
- Límites inferiores exactos según tablas del SAT
</details>

<details>
<summary><strong>¿Puedo usar el sistema desde mi teléfono móvil?</strong></summary>

**Respuesta:** Sí, el sistema es accesible desde navegadores móviles. Sin embargo, para una mejor experiencia recomendamos usarlo desde una computadora de escritorio o laptop.
</details>

<details>
<summary><strong>¿Qué formato debe tener el RFC?</strong></summary>

**Respuesta:** El RFC debe tener exactamente 13 caracteres con el siguiente formato:
- 4 letras (pueden incluir &, Ñ)
- 6 números (fecha: AAMMDD)
- 3 caracteres alfanuméricos

**Ejemplo válido:** `CABA800101ABC`

Todo en **MAYÚSCULAS** y **sin espacios**.
</details>

<details>
<summary><strong>¿Puedo eliminar a un empleado del sistema?</strong></summary>

**Respuesta:** Actualmente, el sistema no permite eliminar empleados desde la interfaz de usuario. Si necesita eliminar un empleado, contacte al área de TI.
</details>

<details>
<summary><strong>¿El sistema calcula aguinaldo o PTU?</strong></summary>

**Respuesta:** Actualmente, el sistema solo calcula el ISR sobre el salario bruto regular. Los cálculos de aguinaldo, PTU u otros conceptos especiales deben realizarse de forma manual.
</details>

---

## 11. Solución de Problemas

### 🔧 Problemas Comunes y Soluciones

#### Problema 1: No puedo acceder al sistema

**Síntomas:**
- La página no carga
- Aparece error "No se puede acceder a este sitio"

**Soluciones:**
1. ✅ Verifique su conexión a internet
2. ✅ Verifique que escribió correctamente la URL
3. ✅ Intente con otro navegador
4. ✅ Contacte al área de TI para verificar que el servidor está funcionando

---

#### Problema 2: "Credenciales inválidas" al iniciar sesión

**Síntomas:**
- Aparece mensaje de error al intentar iniciar sesión

**Soluciones:**
1. ✅ Verifique que escribió correctamente su correo electrónico
2. ✅ Verifique que escribió correctamente su contraseña (distingue mayúsculas/minúsculas)
3. ✅ Asegúrese de que su cuenta fue registrada como administrador
4. ✅ Si olvidó su contraseña, contacte a TI

---

#### Problema 3: Error al registrar empleado

**Síntomas:**
- Mensaje "El RFC ya está registrado"
- Mensaje "El correo ya está registrado"

**Soluciones:**
1. ✅ Verifique que el RFC no esté duplicado en sus registros
2. ✅ Verifique que el correo electrónico sea único
3. ✅ Si es un error del sistema, contacte a TI

---

#### Problema 4: Error al registrar nómina

**Síntomas:**
- Mensaje "El salario debe ser mayor a 0.01"
- Mensaje "La fecha de inicio debe ser anterior a la fecha de fin"

**Soluciones:**
1. ✅ Verifique que el salario sea un número positivo mayor a 0.01
2. ✅ Verifique que las fechas estén en orden correcto
3. ✅ Verifique que el formato de fecha sea correcto (DD/MM/AAAA)

---

#### Problema 5: La sesión se cerró inesperadamente

**Síntomas:**
- Fue redirigido a la pantalla de login
- Aparece mensaje "La sesión ha expirado"

**Soluciones:**
1. ✅ Esto es normal por seguridad después de cierto tiempo de inactividad
2. ✅ Simplemente vuelva a iniciar sesión
3. ✅ Para evitarlo, cierre sesión manualmente cuando termine

---

#### Problema 6: Los cálculos parecen incorrectos

**Síntomas:**
- El ISR calculado no coincide con sus cálculos manuales

**Soluciones:**
1. ✅ Verifique que está usando las tablas del SAT 2025
2. ✅ El sistema calcula automáticamente según 11 rangos salariales
3. ✅ Si persiste la duda, contacte a TI para revisión

---

## 12. Glosario de Términos

### Términos Importantes

| Término           | Definición                                                           |
|-------------------|----------------------------------------------------------------------|
| **Administrador** | Usuario con permisos para gestionar nóminas de todos los empleados   |
| **Dashboard**     | Panel principal de administración donde se ve la lista de empleados  |
| **ISR**           | Impuesto Sobre la Renta, calculado automáticamente por el sistema    |
| **JWT**           | Token de autenticación que mantiene su sesión segura                 |
| **Login**         | Inicio de sesión, proceso de autenticarse en el sistema              |
| **Nómina**        | Registro de pago de un empleado en un período específico             |
| **RFC**           | Registro Federal de Contribuyentes, identificador único del empleado |
| **Salario Bruto** | Salario antes de deducciones de impuestos                            |
| **SAT**           | Servicio de Administración Tributaria, autoridad fiscal de México    |
| **Sesión**        | Período de tiempo en que está conectado al sistema                   |
| **URL**           | Dirección web del sistema (ejemplo: http://servidor:8080)            |

### Términos de Nómina

| Término                        | Definición                                                   |
|--------------------------------|--------------------------------------------------------------|
| **Cuota Fija**                 | Cantidad fija de ISR según el rango salarial                 |
| **Excedente**                  | Cantidad que sobrepasa el límite inferior del rango salarial |
| **Límite Inferior**            | Salario mínimo del rango salarial aplicable                  |
| **Período de Nómina**          | Fechas de inicio y fin para el cálculo (quincenal, mensual)  |
| **Porcentaje sobre Excedente** | Tasa de ISR aplicada al excedente del salario                |
| **Rango Salarial**             | Uno de los 11 rangos definidos por el SAT para calcular ISR  |
| **Tablas SAT 2025**            | Tablas oficiales de impuestos vigentes para el año 2025      |

---

## Notas Finales

### ✅ Recuerde Siempre:

1. **Cerrar sesión** cuando termine de usar el sistema
2. **No compartir** su contraseña con otras personas
3. **Verificar** los datos antes de guardar una nómina
4. **Documentar** cualquier eliminación de nóminas
5. **Contactar a TI** si tiene dudas o problemas

### 🔐 Seguridad:

- El sistema usa encriptación para proteger sus contraseñas
- Las sesiones expiran automáticamente por inactividad
- Todos los accesos quedan registrados en el sistema
- Solo los administradores pueden gestionar nóminas

### 📊 Cálculos:

- Los cálculos de ISR son automáticos y precisos
- Se basan en las tablas oficiales del SAT 2025
- No es necesario hacer cálculos manuales
- El sistema considera 11 rangos salariales

---

## Información del Documento

**Nombre del Sistema:** Sistema de Gestión de Nómina  
**Versión del Manual:** 1.0  
**Fecha de Creación:** 5 de diciembre de 2025  
**Última Actualización:** 5 de diciembre de 2025  
**Elaborado por:** Área de Desarrollo de Software  
**Dirigido a:** Personal de Recursos Humanos

---

## Control de Versiones

| Versión | Fecha      | Cambios Realizados          | Autor              |
|---------|------------|-----------------------------|--------------------|
| 1.0     | 05/12/2025 | Creación inicial del manual | Área de Desarrollo |

---

## Anexos

### Anexo A: Tabla de Rangos Salariales ISR 2025

El sistema utiliza 11 rangos salariales según las tablas del SAT:

| Rango | Desde       | Hasta       | Cuota Fija  | % sobre Excedente |
|-------|-------------|-------------|-------------|-------------------|
| 1     | $0.01       | $746.04     | $0.00       | 1.92%             |
| 2     | $746.05     | $6,332.05   | $14.32      | 6.40%             |
| 3     | $6,332.06   | $11,128.01  | $371.83     | 10.88%            |
| 4     | $11,128.02  | $12,935.82  | $893.63     | 16.00%            |
| 5     | $12,935.83  | $15,487.71  | $1,182.88   | 17.92%            |
| 6     | $15,487.72  | $31,236.49  | $1,640.18   | 21.36%            |
| 7     | $31,236.50  | $49,233.00  | $5,004.12   | 23.52%            |
| 8     | $49,233.01  | $93,993.90  | $9,236.89   | 30.00%            |
| 9     | $93,993.91  | $125,325.20 | $22,665.17  | 32.00%            |
| 10    | $125,325.21 | $375,975.61 | $32,691.18  | 34.00%            |
| 11    | $375,975.62 | En adelante | $117,912.32 | 35.00%            |

**Nota:** Estos rangos son utilizados automáticamente por el sistema. No es necesario memorizarlos.

### Anexo B: Ejemplos de RFC Válidos

**Formato:** LLLL + AAMMDD + XXX

**Ejemplos correctos:**
- `CABA800101ABC` - 4 letras + fecha + 3 alfanuméricos
- `MEPR850615XYZ` - Todo en mayúsculas
- `LOGA900320DEF` - Sin espacios
- `RODR750825123` - Puede terminar en números

**Ejemplos incorrectos:**
- `caba800101abc` - ❌ En minúsculas
- `CABA 800101 ABC` - ❌ Con espacios
- `CABA800101` - ❌ Incompleto (faltan 3 caracteres)
- `CABAA800101ABC` - ❌ Demasiadas letras

---

**FIN DEL MANUAL DE USUARIO**

💡 **¿Necesita más información?** Contacte al área de TI

