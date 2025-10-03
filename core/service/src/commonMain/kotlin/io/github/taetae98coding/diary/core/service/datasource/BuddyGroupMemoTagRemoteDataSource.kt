package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memotag.UpdateMemoTagRequestEntity
import io.github.taetae98coding.diary.core.service.entity.memotag.UpdateMemoTagResultEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
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
        body: UpdateMemoTagRequestEntity,
    ): DiaryRemoteEntity<UpdateMemoTagResultEntity> {
        val response = httpClient.put("/v1/buddy-group/$buddyGroupId/memo-tag") {
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
    ): DiaryRemoteEntity<List<MemoTagRemoteEntity>> {
        val response = httpClient.get("/v1/buddy-group/$buddyGroupId/memo-tag") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("updatedAt", updatedAt)
        }

        return response.body()
    }
}
