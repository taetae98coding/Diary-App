package io.github.taetae98coding.diary.core.navigation.buddy.memo

import androidx.navigation3.runtime.NavKey
import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BuddyGroupMemoDetailNavKey(
    @SerialName("buddyGroupId")
    val buddyGroupId: Uuid,
    @SerialName("memoId")
    val memoId: Uuid,
) : NavKey
