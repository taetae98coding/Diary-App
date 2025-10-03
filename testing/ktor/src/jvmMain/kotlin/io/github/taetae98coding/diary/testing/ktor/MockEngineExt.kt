package io.github.taetae98coding.diary.testing.ktor

import io.github.taetae98coding.diary.testing.resources.resourceAsText
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

public fun mockEngine(
    fileName: String,
    status: HttpStatusCode = HttpStatusCode.OK,
): HttpClientEngine {
    return MockEngine { request ->
        respond(
            content = resourceAsText(fileName),
            status = status,
            headers = headersOf(HttpHeaders.ContentType, "application/json"),
        )
    }
}
