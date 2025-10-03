package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.AddBuddyGroupMemoRequestEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.AddBuddyGroupMemoResultEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoDeleteRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoDetailRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoFinishRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoPrimaryRemoteEntity
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
        body: AddBuddyGroupMemoRequestEntity,
    ): DiaryRemoteEntity<AddBuddyGroupMemoResultEntity> {
        val response = httpClient.post("/v1/buddy-group/$buddyGroupId/memo") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }

    public suspend fun update(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        body: MemoDetailRemoteEntity,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo/$memoId") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }

    public suspend fun updateFinished(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        body: MemoFinishRemoteEntity,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo/$memoId/finished") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }

    public suspend fun updateDeleted(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        body: MemoDeleteRemoteEntity,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo/$memoId/deleted") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }

    public suspend fun updatePrimaryTag(
        token: String,
        buddyGroupId: Uuid,
        memoId: Uuid,
        body: MemoPrimaryRemoteEntity,
    ): DiaryRemoteEntity<MemoRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo/$memoId/primary-tag") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }

    public suspend fun fetch(
        token: String,
        buddyGroupId: Uuid,
        updatedAt: Long,
    ): DiaryRemoteEntity<List<MemoRemoteEntity>> {
        val response = httpClient.get("/v1/buddy-group/$buddyGroupId/memo") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("updatedAt", updatedAt)
        }

        return response.body()
    }
}
