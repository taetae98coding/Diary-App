package io.github.taetae98coding.diary.testing.resources

public fun Any.resourceAsText(
    fileName: String,
): String {
    return this::class.java
        .getResource(fileName)!!
        .readText()
}
