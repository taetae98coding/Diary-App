package io.github.taetae98coding.diary.core.navigation.memo

import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.SyncNavKey
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MemoAddNavKey(
    @SerialName("primaryTag")
    val primaryTag: Uuid? = null,
    @SerialName("start")
    val start: LocalDate? = null,
    @SerialName("endInclusive")
    val endInclusive: LocalDate? = null,
) : NavKey,
    SyncNavKey
