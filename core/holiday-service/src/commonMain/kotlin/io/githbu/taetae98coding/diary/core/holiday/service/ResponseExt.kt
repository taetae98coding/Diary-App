package io.githbu.taetae98coding.diary.core.holiday.service

import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayItemEntity
import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayItemsEntity
import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.githbu.taetae98coding.diary.core.holiday.service.entity.OpenApiEntity
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

internal suspend fun HttpResponse.holidayRemoteEntity(
    json: Json,
    dispatcher: CoroutineDispatcher,
): List<HolidayRemoteEntity> {
    val body = body<OpenApiEntity>()
        .response
        .body

    return when (body.totalCount) {
        0 -> {
            emptyList()
        }

        1 -> {
            val items = withContext(dispatcher) { json.decodeFromJsonElement<HolidayItemEntity>(body.items) }
            listOf(items.item)
        }

        else -> {
            val items = withContext(dispatcher) { json.decodeFromJsonElement<HolidayItemsEntity>(body.items) }
            items.item
        }
    }
}
