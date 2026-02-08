# iOS App (SwiftUI)

Este directorio contiene el código SwiftUI (scaffold) para la app iOS.

⚠️ Nota: Desde Windows no se puede compilar ni ejecutar iOS (requiere Xcode/macOS).
Aun así:
- El módulo `:shared` (KMP) define targets iOS y genera el framework en CI (macOS runner).
- Cuando se disponga de macOS, se creará el proyecto Xcode dentro de `iosApp/` y se linkará `shared.framework`.

## Próximos pasos (macOS)
1. Crear proyecto SwiftUI en Xcode dentro de `iosApp/`
2. Añadir `shared.framework` como dependencia
3. Inicializar Koin desde Swift (`SharedBootstrap.start()`)
4. Consumir `StateFlow`/ViewModels desde SwiftUI
