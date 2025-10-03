package io.github.taetae98coding.diary.core.work

import kotlin.uuid.Uuid

internal data object WorkManagerConstValue {
    fun syncWorkName(accountId: Uuid) = "DIARY_SYNC_$accountId"
}
