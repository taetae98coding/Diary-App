package io.github.taetae98coding.diary.library.koin.datastore

import io.github.taetae98coding.diary.library.kotlinx.file.tempPath

public data object KoinComponentDataStore {
    private var storePath: String = tempPath()

    public fun storePath(storePath: String): KoinComponentDataStore {
        this.storePath = storePath
        return this
    }

    internal fun storePath(): String {
        return storePath
    }
}
