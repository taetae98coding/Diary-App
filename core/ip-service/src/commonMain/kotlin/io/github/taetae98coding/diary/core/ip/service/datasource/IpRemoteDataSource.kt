package io.github.taetae98coding.diary.core.ip.service.datasource

import io.github.taetae98coding.diary.core.ip.service.entity.LocationRemoteEntity
import io.github.taetae98coding.diary.core.ktor.client.di.IpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Factory

@Factory
public class IpRemoteDataSource internal constructor(
    @param:IpClient
    private val client: HttpClient,
) {
    public suspend fun get(): LocationRemoteEntity {
        val response = client.get("json")
        return response.body()
    }
}
