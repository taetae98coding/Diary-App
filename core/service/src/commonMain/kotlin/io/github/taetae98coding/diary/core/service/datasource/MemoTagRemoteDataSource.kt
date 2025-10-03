package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import org.koin.core.annotation.Factory

@Factory
public class MemoTagRemoteDataSource internal constructor(
    @param:DiaryClient
    private val httpClient: HttpClient,
) {
    public suspend fun upsert(
        token: String,
        body: List<MemoTagRemoteEntity>,
    ): DiaryRemoteEntity<List<MemoTagRemoteEntity>> {
        val response = httpClient.post("/v1/memo-tag") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(body)
        }

        return response.body()
    }
}
