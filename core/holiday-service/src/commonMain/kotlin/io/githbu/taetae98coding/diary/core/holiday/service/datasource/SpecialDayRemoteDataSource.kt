package io.githbu.taetae98coding.diary.core.holiday.service.datasource

import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.githbu.taetae98coding.diary.core.holiday.service.holidayRemoteEntity
import io.github.taetae98coding.diary.core.coroutines.di.IoDispatcher
import io.github.taetae98coding.diary.core.ktor.client.di.ApiJson
import io.github.taetae98coding.diary.core.ktor.client.di.HolidayClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.datetime.YearMonth
import kotlinx.datetime.number
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory

@Factory
public class SpecialDayRemoteDataSource internal constructor(
    @param:HolidayClient
    private val client: HttpClient,
    @param:ApiJson
    private val json: Json,
    @param:IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {
    public suspend fun get(yearMonth: YearMonth): List<HolidayRemoteEntity> {
        val response = client.get("getSundryDayInfo") {
            parameter("solYear", yearMonth.year)
            parameter("solMonth", yearMonth.month.number.toString().padStart(2, '0'))
        }

        return response.holidayRemoteEntity(json, ioDispatcher)
    }
}
