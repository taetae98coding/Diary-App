package io.github.taetae98coding.diary.core.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DiaryPagingDataEntity<T>(
    @SerialName("count")
    val count: Int,
    @SerialName("data")
    val data: List<T>,
)
