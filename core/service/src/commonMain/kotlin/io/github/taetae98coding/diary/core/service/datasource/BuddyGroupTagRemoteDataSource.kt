package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryPagingDataEntity
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.AddBuddyGroupTagRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.tag.TagDetailRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
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
public class BuddyGroupTagRemoteDataSource internal constructor(
    @param:DiaryClient
    private val httpClient: HttpClient,
) {
    public suspend fun add(
        token: String,
        buddyGroupId: Uuid,
        entity: AddBuddyGroupTagRemoteEntity,
    ): DiaryRemoteEntity<TagRemoteEntity> {
        val response = httpClient.post("/v1/buddy-group/$buddyGroupId/tag") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(entity)
        }

        return response.body()
    }

    public suspend fun update(
        token: String,
        buddyGroupId: Uuid,
        tagId: Uuid,
        detail: TagDetailRemoteEntity,
    ): DiaryRemoteEntity<TagRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/tag/$tagId") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(detail)
        }

        return response.body()
    }

    public suspend fun updateFinished(
        token: String,
        buddyGroupId: Uuid,
        tagId: Uuid,
        isFinished: Boolean,
    ): DiaryRemoteEntity<TagRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/tag/$tagId/isFinished") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("isFinished", isFinished)
        }

        return response.body()
    }

    public suspend fun updateDeleted(
        token: String,
        buddyGroupId: Uuid,
        tagId: Uuid,
        isDeleted: Boolean,
    ): DiaryRemoteEntity<TagRemoteEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/tag/$tagId/isDeleted") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("isDeleted", isDeleted)
        }

        return response.body()
    }

    public suspend fun page(
        token: String,
        buddyGroupId: Uuid,
        limit: Int,
        offset: Int,
    ): DiaryRemoteEntity<DiaryPagingDataEntity<TagRemoteEntity>> {
        val response = httpClient.get("/v1/buddy-group/$buddyGroupId/tag") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("limit", limit)
            parameter("offset", offset)
        }

        return response.body()
    }
}
