package io.github.taetae98coding.diary.core.navigation.tag

import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.SyncNavKey
import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class TagMemoNavKey(
    @SerialName("tagId")
    val tagId: Uuid,
) : NavKey,
    SyncNavKey
