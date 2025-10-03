package io.github.taetae98coding.diary.core.navigation.buddy.tag

import androidx.navigation3.runtime.NavKey
import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BuddyGroupFinishedTagNavKey(
    @SerialName("buddyGroupId")
    val buddyGroupId: Uuid,
) : NavKey
