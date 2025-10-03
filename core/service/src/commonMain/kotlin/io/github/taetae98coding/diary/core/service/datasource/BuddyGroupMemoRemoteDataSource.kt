package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryPagingDataEntity
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.AddBuddyGroupMemoRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoDetailRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupMemoRemoteDataSource internal constructor(
    @param:DiaryClient
    private val httpClient: HttpClient,
) {
    public suspend fun add(
        token: String,
        buddyGroupId: Uuid,
        entity: AddBuddyGroupMemoRemoteEntity,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.post("/v1/buddy-group/$buddyGroupId/memo") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(entity)
        }

        return response.body()
    }

    public suspend fun update(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        detail: MemoDetailRemoteEntity,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo/$memoId") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(detail)
        }

        return response.body()
    }

    public suspend fun updateFinished(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        isFinished: Boolean,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo/$memoId/isFinished") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("isFinished", isFinished)
        }

        return response.body()
    }

    public suspend fun updateDeleted(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        isDeleted: Boolean,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo/$memoId/isDeleted") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("isDeleted", isDeleted)
        }

        return response.body()
    }

    public suspend fun updatePrimaryTag(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        tagId: Uuid?,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo/$memoId/primary-tag") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("tagId", tagId)
        }

        return response.body()
    }

    // TODO primary tag 받을 수 있도록 수정
    // FOREIGN KEY Error 발생
    public suspend fun page(
        token: String,
        buddyGroupId: Uuid,
        limit: Int,
        offset: Int,
    ): DiaryRemoteEntity<DiaryPagingDataEntity<MemoRemoteEntity>> {
        val response = httpClient.get("/v1/buddy-group/$buddyGroupId/memo") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("limit", limit)
            parameter("offset", offset)
        }

        return response.body()
    }
}
