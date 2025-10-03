import DiaryKotlin
import SwiftUI

struct ContentView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return IosAppKt.DiaryUIViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {

    }
}
