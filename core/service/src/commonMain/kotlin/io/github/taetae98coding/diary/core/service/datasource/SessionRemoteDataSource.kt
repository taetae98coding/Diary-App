package io.github.taetae98coding.diary.core.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.account.SessionRemoteEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Factory

@Factory
public class SessionRemoteDataSource internal constructor(
    @param:DiaryClient
    private val client: HttpClient,
) {
    public suspend fun getGoogleSession(
        idToken: String,
    ): DiaryRemoteEntity<SessionRemoteEntity> {
        val response = client.get("/v1/session/google") {
            parameter("idToken", idToken)
        }

        return response.body()
    }
}
