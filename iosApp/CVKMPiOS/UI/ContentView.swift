import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack(spacing: 12) {
            Text("CV KMP (iOS)")
                .font(.title)

            Text("SwiftUI UI (placeholder)")
                .font(.subheadline)

            Text("Shared KMP will be wired when macOS/Xcode is available.")
                .font(.footnote)
                .multilineTextAlignment(.center)
                .padding(.horizontal, 16)
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
