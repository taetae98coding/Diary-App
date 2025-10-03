plugins {
    id("diary.primitive.android.library")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.lifecycle.common"
    }

    dependencies {
        implementation(libs.lifecycle.common)
    }
}
