import SwiftUI

@main
struct CVKMPApp: App {
    init() {
        SharedBootstrap.start()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
