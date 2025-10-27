package io.github.taetae98coding.diary.core.navigation.memo

import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.SyncNavKey
import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MemoDetailNavKey(
    @SerialName("memoId")
    val memoId: Uuid,
) : NavKey,
    SyncNavKey
