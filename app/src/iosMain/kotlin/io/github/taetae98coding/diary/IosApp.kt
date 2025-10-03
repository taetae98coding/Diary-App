package io.github.taetae98coding.diary

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

@Suppress("FunctionName")
public fun DiaryUIViewController(): UIViewController {
    return ComposeUIViewController {
        App()
    }
}
