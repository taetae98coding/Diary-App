package io.github.taetae98coding.diary.core.ktor.client

import io.github.taetae98coding.diary.core.ktor.client.di.ApiJson
import io.ktor.serialization.kotlinx.json.DefaultJson
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
internal class ApiJsonModule {
    @Single
    @ApiJson
    fun providesApiJson(): Json {
        return Json(DefaultJson) {
            ignoreUnknownKeys = true
        }
    }
}
