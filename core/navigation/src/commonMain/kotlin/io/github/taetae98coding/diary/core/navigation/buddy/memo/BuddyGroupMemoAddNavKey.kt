package io.github.taetae98coding.diary.core.navigation.buddy.memo

import androidx.navigation3.runtime.NavKey
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BuddyGroupMemoAddNavKey(
    @SerialName("buddyGroupId")
    val buddyGroupId: Uuid,
    @SerialName("primaryTag")
    val primaryTag: Uuid? = null,
    @SerialName("start")
    val start: LocalDate? = null,
    @SerialName("endInclusive")
    val endInclusive: LocalDate? = null,
) : NavKey
