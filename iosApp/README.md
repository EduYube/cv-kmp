### iOS App (SwiftUI)

Este módulo contendrá la aplicación iOS en SwiftUI.

La lógica compartida proviene del módulo `:shared` (Kotlin Multiplatform),
que se exporta como framework iOS.

Desde Windows no se puede compilar Swift/Xcode.
La validación del target iOS se realiza en CI (macOS runner).

Cuando se disponga de macOS:
- Crear proyecto SwiftUI en esta carpeta
- Importar shared.framework
- Conectar StateFlow/UseCases desde Kotlin
