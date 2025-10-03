package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryPagingDataEntity
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyAddRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupDetailRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupRemoteEntity
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
public class BuddyGroupRemoteDataSource internal constructor(
    @param:DiaryClient
    private val client: HttpClient,
) {
    public suspend fun add(
        token: String,
        body: BuddyAddRemoteEntity,
    ): DiaryRemoteEntity<BuddyGroupRemoteEntity> {
        val response = client.post("/v1/buddy-group") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }

    public suspend fun update(
        token: String,
        buddyGroupId: Uuid,
        body: BuddyGroupDetailRemoteEntity,
    ): DiaryRemoteEntity<BuddyGroupRemoteEntity> {
        val response = client.put("/v1/buddy-group/$buddyGroupId") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }

    public suspend fun get(
        token: String,
        buddyGroupId: Uuid,
    ): DiaryRemoteEntity<BuddyGroupRemoteEntity> {
        val response = client.get("/v1/buddy-group/$buddyGroupId") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        return response.body()
    }

    public suspend fun page(
        token: String,
        limit: Int,
        offset: Int,
    ): DiaryRemoteEntity<DiaryPagingDataEntity<BuddyGroupRemoteEntity>> {
        val response = client.get("/v1/buddy-group") {
            header(HttpHeaders.Authorization, "Bearer $token")

            parameter("limit", limit)
            parameter("offset", offset)
        }

        return response.body()
    }
}
