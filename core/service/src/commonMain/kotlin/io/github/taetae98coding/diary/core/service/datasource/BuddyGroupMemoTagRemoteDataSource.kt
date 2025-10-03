package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.http.HttpHeaders
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupMemoTagRemoteDataSource internal constructor(
    @param:DiaryClient
    private val httpClient: HttpClient,
) {
    public suspend fun updateMemoTag(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        tagId: Uuid,
        isMemoTag: Boolean,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo-tag") {
            header(HttpHeaders.Authorization, "Bearer $token")

            parameter("memoId", memoId)
            parameter("tagId", tagId)
            parameter("isMemoTag", isMemoTag)
        }

        return response.body()
    }
}
