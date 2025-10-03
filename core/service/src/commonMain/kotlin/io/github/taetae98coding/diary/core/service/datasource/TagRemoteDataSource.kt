package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class TagRemoteDataSource internal constructor(
    @param:DiaryClient
    private val httpClient: HttpClient,
) {
    public suspend fun get(token: String, tagId: Uuid): DiaryRemoteEntity<TagRemoteEntity> {
        val response = httpClient.get("/v1/tag/$tagId") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        return response.body()
    }

    public suspend fun upsert(
        token: String,
        body: List<TagRemoteEntity>,
    ): DiaryRemoteEntity<List<TagRemoteEntity>> {
        val response = httpClient.post("/v1/tag") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }
}
