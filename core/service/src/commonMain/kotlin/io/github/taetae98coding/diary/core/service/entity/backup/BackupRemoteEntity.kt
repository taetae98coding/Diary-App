package io.github.taetae98coding.diary.core.service.entity.backup

import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import kotlinx.serialization.Serializable

@Serializable
public data class BackupRemoteEntity(
    val memo: List<MemoRemoteEntity>,
) {
    public fun isEmpty(): Boolean {
        return memo.isEmpty()
    }
}
