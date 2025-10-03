package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.http.HttpHeaders
import org.koin.core.annotation.Factory

@Factory
public class FcmRemoteDataSource internal constructor(
    @param:DiaryClient
    private val client: HttpClient,
) {
    public suspend fun register(accountToken: String?, fcmToken: String): DiaryRemoteEntity<Unit> {
        val response = client.put("/v1/push-token/fcm/$fcmToken") {
            if (!accountToken.isNullOrBlank()) {
                header(HttpHeaders.Authorization, "Bearer $accountToken")
            }
        }

        return response.body()
    }
}
