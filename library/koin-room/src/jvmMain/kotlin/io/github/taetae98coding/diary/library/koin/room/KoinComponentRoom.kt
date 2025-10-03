package io.github.taetae98coding.diary.library.koin.room

import io.github.taetae98coding.diary.library.kotlinx.file.tempPath

public data object KoinComponentRoom {
    private var storePath: String = tempPath()

    public fun storePath(storePath: String): KoinComponentRoom {
        this.storePath = storePath
        return this
    }

    internal fun storePath(): String {
        return storePath
    }
}
