[![CI](https://github.com/EduYube/cv-kmp/actions/workflows/ci.yml/badge.svg)](https://github.com/EduYube/cv-kmp/actions/workflows/ci.yml)
# CV KMP

App de CV construida con Kotlin Multiplatform (KMP) para compartir lógica entre Android e iOS.
- Android UI: Jetpack Compose (nativo)
- iOS UI: SwiftUI (nativo)

## Commit convention (Conventional Commits)

Format:
`type(scope): description`

Types we use:
- feat, fix, docs, chore, refactor, test, build, ci

Common scopes in this repo:
- shared, shared-di, domain, data, presentation, android, ios, ci, build

Examples:
- feat(shared-di): bootstrap koin with platform modules
- fix(android): use unique namespace for shared module
- docs(shared): document koin bootstrap and initKoin api decision

### Local git commit template
Run:
`git config commit.template .gitmessage`

## Modules
- `app`: Android application
- `shared`: KMP shared module (domain/data/presentation)
- `iosApp`: scaffold/documentación para la app iOS (se compilará en macOS)

## Build
### Android
- `./gradlew :app:assembleDebug`
- Ejecutar desde Android Studio

### Shared
- `./gradlew :shared:build`

### iOS (CI / macOS)
El framework iOS se valida en CI (runner macOS) ejecutando tasks `linkDebugFramework...`.
En Windows no se puede compilar iOS (requiere Xcode).

## Architecture

This project follows a Kotlin Multiplatform (KMP) structure.

### Modules

- `app` → Android application module (Jetpack Compose UI)
- `shared` → Kotlin Multiplatform module
    - `commonMain` → Shared business logic
    - `androidMain` → Android-specific implementations
    - `iosMain` → iOS-specific implementations
- `iosApp` → Swift iOS application (consumer of shared module)

### Dependency Flow

Both Android and iOS apps depend on the `shared` module.
```mermaid

graph TB
  %% ========== Apps ==========
  subgraph APPS["Apps"]
    AND_APP[":app (Android)"] --> AND_UI["Android UI<br/>(Jetpack Compose)"]
    IOS_APP["iosApp (iOS)"] --> IOS_UI["iOS UI<br/>(SwiftUI)"]
  end

  %% ========== Shared KMP ==========
  AND_UI --> API
  IOS_UI --> API

  subgraph SHARED[":shared (KMP)"]
    API["Shared public API<br/>(ViewModels / Facades)"] --> DI["DI (Koin)"]

    DI --> DOMAIN
    DI --> DATA

    subgraph DOMAIN["Domain"]
      UC["Use cases"] --> REPO["Repository interfaces"]
      ENT["Entities / Models"]
      UC --> ENT
    end

    subgraph DATA["Data"]
      REPO_IMPL["Repository implementations"] --> DS_LOCAL["Local datasource"]
      REPO_IMPL --> DS_REMOTE["Remote datasource"]

      DS_LOCAL --> STORAGE["Storage<br/>(DB / Preferences / Files)"]
      DS_REMOTE --> NET["Network<br/>(HTTP / API)"]
    end

    REPO --> REPO_IMPL
  end

  %% ========== Source sets ==========
  subgraph SOURCES["Source sets"]
    COMMON["commonMain<br/>(shared logic)"]
    AND_MAIN["androidMain<br/>(Android impl)"]
    IOS_MAIN["iosMain<br/>(iOS impl)"]
  end

  SHARED --- COMMON
  SHARED --- AND_MAIN
  SHARED --- IOS_MAIN

  %% Platform-specific wiring into data/local
  AND_MAIN --> DS_LOCAL
  IOS_MAIN --> DS_LOCAL

```

---