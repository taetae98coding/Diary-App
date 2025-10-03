package io.github.taetae98coding.diary.core.holiday.service

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.githbu.taetae98coding.diary.core.holiday.service.datasource.HolidayRemoteDataSource
import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.github.taetae98coding.diary.testing.ktor.mockEngine
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class HolidayRemoteDataSourceTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("getRestDeInfo_no.json") {
            val engine = mockEngine("/getRestDeInfo_no.json")
            val application = startKoin {
                modules(fakeHolidayClientModule(engine))
            }
            val dataSource = application.koin.get<HolidayRemoteDataSource>()
            val response = dataSource.get(fixture.giveMeOne())

            response.shouldBeEmpty()

            stopKoin()
        }

        test("getRestDeInfo_one.json") {
            val engine = mockEngine("/getRestDeInfo_one.json")
            val application = startKoin {
                modules(fakeHolidayClientModule(engine))
            }
            val dataSource = application.koin.get<HolidayRemoteDataSource>()
            val response = dataSource.get(fixture.giveMeOne())

            response shouldHaveSize 1
            response.first() shouldBe HolidayRemoteEntity(
                name = "기독탄신일",
                localDate = LocalDate(2024, 12, 25),
            )

            stopKoin()
        }

        test("getRestDeInfo_many.json") {
            val engine = mockEngine("/getRestDeInfo_many.json")
            val application = startKoin {
                modules(fakeHolidayClientModule(engine))
            }
            val dataSource = application.koin.get<HolidayRemoteDataSource>()
            val response = dataSource.get(fixture.giveMeOne())

            response shouldHaveSize 5
            response.first() shouldBe HolidayRemoteEntity(
                name = "1월1일",
                localDate = LocalDate(2025, 1, 1),
            )

            stopKoin()
        }
    }
}
