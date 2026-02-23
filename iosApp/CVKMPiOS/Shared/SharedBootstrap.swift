import Foundation

enum SharedBootstrap {
    static func start() {
        // TODO: When Xcode project exists and shared.framework is linked:
        // - init Koin DONE!
        // Example:
        // SharedModuleKt.initKoin() DONE!
        // Then
        // - create root view models
        KoinKt.initKoin(appDeclaration: { _ in })
    }
}
