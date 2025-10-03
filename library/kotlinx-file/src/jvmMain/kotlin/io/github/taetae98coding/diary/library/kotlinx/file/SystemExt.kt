package io.github.taetae98coding.diary.library.kotlinx.file

public fun tempPath(): String {
    return System.getProperty("java.io.tmpdir")
}

public fun homePath(): String {
    return System.getProperty("user.home")
}
