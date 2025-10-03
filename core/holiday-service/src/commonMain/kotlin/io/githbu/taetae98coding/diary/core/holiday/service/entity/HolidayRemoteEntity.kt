package io.githbu.taetae98coding.diary.core.holiday.service.entity

import io.githbu.taetae98coding.diary.core.holiday.service.serialization.HolidayLocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class HolidayRemoteEntity(
    @SerialName("dateName")
    val name: String,
    @Serializable(HolidayLocalDateSerializer::class)
    @SerialName("locdate")
    val localDate: LocalDate,
)
