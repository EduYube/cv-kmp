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