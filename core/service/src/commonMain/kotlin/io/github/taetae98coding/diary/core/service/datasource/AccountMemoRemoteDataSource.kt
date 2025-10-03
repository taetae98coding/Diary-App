package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import org.koin.core.annotation.Factory

@Factory
public class AccountMemoRemoteDataSource internal constructor(
    @param:DiaryClient
    private val httpClient: HttpClient,
) {
    public suspend fun fetch(token: String, updatedAt: Long): DiaryRemoteEntity<List<MemoRemoteEntity>> {
        val response = httpClient.get("/v1/memo") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("updatedAt", updatedAt)
        }

        return response.body()
    }
}
