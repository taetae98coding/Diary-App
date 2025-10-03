package io.github.taetae98coding.diary

import androidx.compose.ui.window.singleWindowApplication
import io.github.taetae98coding.diary.initializer.Initializer

public suspend fun main() {
    Initializer.init()

    singleWindowApplication(
        title = BuildKonfig.APP_NAME,
    ) {
//        KoinMultiplatformApplication(
//            config = KoinConfiguration {
//                modules(
//                    AppModule().module,
//                    JvmAppModule().module,
//                    NonAndroidAppModule().module,
//                )
//            },
//        ) {
//            App()
//        }

        App()
    }
}
