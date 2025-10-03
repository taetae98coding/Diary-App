package io.github.taetae98coding.diary.core.ip.service

import io.github.taetae98coding.diary.core.ip.service.datasource.IpRemoteDataSource
import io.github.taetae98coding.diary.core.ip.service.entity.LocationRemoteEntity
import io.github.taetae98coding.diary.testing.ktor.mockEngine
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class IpRemoteDataSourceTest : FunSpec() {
    init {
        test("json.json") {
            val engine = mockEngine("/json.json")
            val application = startKoin {
                modules(fakeIpClientModule(engine))
            }
            val dataSource = application.koin.get<IpRemoteDataSource>()
            val response = dataSource.get()

            response shouldBe LocationRemoteEntity(
                latitude = 37.5247,
                longitude = 126.9133,
            )

            stopKoin()
        }
    }
}
