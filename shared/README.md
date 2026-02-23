# Shared module – Arquitectura y DI (Koin) - Sprint 2

Este módulo `shared` es el **core común** de la app CV en KMP.  
Desde aquí consumen tanto la app Android (`app`) como la app iOS (`iosApp`).

La idea es que aquí viva **toda la lógica compartida**, sin depender de frameworks de UI ni de detalles de plataforma.

---
.

### 1. Dependencias y Version Catalog

Se ha añadido **Koin** como framework de inyección de dependencias usando el  
**version catalog (`libs.versions.toml`)**, que ya existía en el proyecto desde el Sprint 1.

Koin se ha declarado en:

- `commonMain`: porque lo usamos en el código compartido.
- `commonTest`: porque se usará para testear lógica compartida (use cases, repos, etc.).

Esto permite que todo lo relacionado con DI viva **en el core**, y no duplicar lógica entre plataformas.

---

### 2. Rol del módulo `shared`

El módulo `shared` es visto por:

- Android (`app`)
- iOS (`iosApp`)

Aquí es donde se inicializa Koin **una sola vez**, y donde se definen:

- módulos comunes
- contratos
- lógica independiente de plataforma

Por eso todo lo relacionado con Koin **arranca aquí**, no en Android ni en iOS directamente.

---

### 3. Inicialización de Koin (`initKoin`)

Se ha creado una función `initKoin()` que se encarga de arrancar el contenedor de dependencias.

Esta función:

- inicializa Koin
- carga los módulos comunes
- carga un módulo específico por plataforma

La estructura es esta:

- `commonModule`: módulo común donde irán repos, use cases, viewmodels compartidos, etc.
- `platformModule()`: módulo específico por plataforma (Android / iOS)

---

### 4. Uso de `expect / actual` para módulos de plataforma

Para poder tener dependencias específicas por plataforma, se usa el mecanismo  
**`expect / actual` de Kotlin Multiplatform**.

- En `commonMain` se declara:
    - una función `expect fun platformModule(): Module`
- En `androidMain` y `iosMain` se implementa:
    - `actual fun platformModule(): Module`

Aunque esté en `iosMain`, **sigue siendo Kotlin**, no Swift.

Esto permite que el compilador sepa, para cada target, qué implementación usar.

El matching entre `expect` y `actual` se hace por:

- mismo package
- mismo nombre
- misma firma

No hay magia extra.

---

## Decisión importante: firma de `initKoin()`

### Qué problema apareció

Inicialmente, `initKoin()` devolvía un tipo de Koin (`KoinApplication`).

Eso provocó un error en Android del estilo:

```
Cannot access class 'org.koin.core.KoinApplication'
Check your module classpath for missing or conflict dependencies
```

La causa no era un bug, sino **una decisión de arquitectura**:

- Koin estaba declarado como `implementation` en `shared`
- pero `shared` estaba **exponiendo tipos de Koin en su API pública**
- el módulo `app` no tenía acceso a esos tipos en compile-time

---

### Opciones que se valoraron

1. Declarar Koin como `api(...)`  
   → Exponer Koin hacia fuera.

2. Añadir Koin también como dependencia del módulo `app`  
   → Acoplar Android a una decisión interna de `shared`.

3. **No exponer Koin en la API pública de `shared`**  
   → Mantener Koin como detalle interno.

---

### Decisión tomada (y por qué)

Se ha elegido la **opción 3**.

`initKoin()` **no devuelve nada** y no expone tipos de Koin:

```kotlin
fun initKoin() {
    startKoin {
        modules(commonModule, platformModule())
    }
}
```
#### Por qué esta opción es la correcta en KMP

- `shared` expone **su propia API**, no la de Koin.
- Las apps consumidoras (Android / iOS) **no necesitan saber que usamos Koin**.
- Se reduce el acoplamiento entre módulos.
- Evitamos fugas de dependencias a futuro.
- Es la opción más limpia y escalable para proyectos KMP.

Esta decisión **no tiene impactos negativos a futuro**.

Si más adelante necesitamos:

- logging
- configuración por plataforma
- flags de debug

Se pueden pasar como **parámetros propios**, sin filtrar Koin hacia fuera.

---

## Estado actual

A día de hoy:

- Koin está integrado correctamente en `shared`.
- La arquitectura está preparada para crecer.
- No hay deuda técnica escondida.
- Las decisiones tomadas son conscientes y documentadas.
