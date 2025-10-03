package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryPagingDataEntity
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupTagMemoRemoteDataSource internal constructor(
    @param:DiaryClient
    private val httpClient: HttpClient,
) {
    public suspend fun page(
        token: String,
        buddyGroupId: Uuid,
        tagId: Uuid,
        limit: Int,
        offset: Int,
    ): DiaryRemoteEntity<DiaryPagingDataEntity<MemoRemoteEntity>> {
        val response = httpClient.get("/v1/buddy-group/$buddyGroupId/tag/$tagId/memo") {
            header("Authorization", "Bearer $token")

            parameter("limit", limit)
            parameter("offset", offset)
        }

        return response.body()
    }
}
