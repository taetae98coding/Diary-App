package io.githbu.taetae98coding.diary.core.holiday.service.serialization

import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal data object HolidayLocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor = PrimitiveSerialDescriptor("HolidayLocalDate", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeInt(value.year * 10000 + value.month.number * 100 + value.day)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val value = decoder.decodeInt()

        val year = value / 10000
        val month = (value % 10000) / 100
        val day = value % 100

        return LocalDate(year, month, day)
    }
}
