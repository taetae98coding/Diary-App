package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupCalendarMemoRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupCalendarMemoRemoteDataSource internal constructor(
    @param:DiaryClient
    private val httpClient: HttpClient,
) {
    public suspend fun get(
        token: String,
        buddyGroupId: Uuid,
        dateRange: LocalDateRange,
    ): DiaryRemoteEntity<BuddyGroupCalendarMemoRemoteEntity> {
        val response = httpClient.get("/v1/buddy-group/$buddyGroupId/calendar/memo") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("start", dateRange.start)
            parameter("endInclusive", dateRange.endInclusive)
        }

        return response.body()
    }
}
