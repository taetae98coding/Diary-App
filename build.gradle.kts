plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.multiplatform.library).apply(false)
    alias(libs.plugins.kotlin.plugin.compose).apply(false)
    alias(libs.plugins.kotlin.plugin.serialization).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.compose.multiplatform).apply(false)
    alias(libs.plugins.google.services).apply(false)
    alias(libs.plugins.firebase.crashlytics).apply(false)
    alias(libs.plugins.firebase.perf).apply(false)
    alias(libs.plugins.androidx.room).apply(false)
    alias(libs.plugins.buildkonfig).apply(false)
    alias(libs.plugins.compose.hot.reload).apply(false)
    alias(libs.plugins.dependency.guard).apply(false)
    alias(libs.plugins.module.graph)
}

subprojects {
    afterEvaluate {
        plugins.apply("com.jraska.module.graph.assertion")

        moduleGraphAssert {
            configurations += setOf("commonMainImplementation", "commonMainApi", "implementation", "api")
        }
    }
}
