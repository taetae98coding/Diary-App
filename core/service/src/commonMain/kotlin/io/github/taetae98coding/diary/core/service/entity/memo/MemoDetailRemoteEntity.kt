package io.github.taetae98coding.diary.core.service.entity.memo

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MemoDetailRemoteEntity(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("start")
    val start: LocalDate?,
    @SerialName("endInclusive")
    val endInclusive: LocalDate? = null,
    @SerialName("color")
    val color: Int,
)
