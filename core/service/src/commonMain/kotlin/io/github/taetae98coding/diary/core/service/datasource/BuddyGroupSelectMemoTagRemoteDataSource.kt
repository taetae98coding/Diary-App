package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryPagingDataEntity
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.tag.SelectMemoTagRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupSelectMemoTagRemoteDataSource internal constructor(
    @param:DiaryClient
    private val client: HttpClient,
) {
    public suspend fun page(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        limit: Int,
        offset: Int,
    ): DiaryRemoteEntity<DiaryPagingDataEntity<SelectMemoTagRemoteEntity>> {
        val response = client.get("/v1/buddy-group/$buddyGroupId/select-memo-tag") {
            header("Authorization", "Bearer $token")
            parameter("memoId", memoId)
            parameter("limit", limit)
            parameter("offset", offset)
        }

        return response.body()
    }
}
