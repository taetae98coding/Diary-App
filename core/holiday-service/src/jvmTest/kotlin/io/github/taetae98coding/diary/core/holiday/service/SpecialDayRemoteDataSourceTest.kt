package io.github.taetae98coding.diary.core.holiday.service

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.githbu.taetae98coding.diary.core.holiday.service.datasource.SpecialDayRemoteDataSource
import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.github.taetae98coding.diary.testing.ktor.mockEngine
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class SpecialDayRemoteDataSourceTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("getSundryDayInfo_no.json") {
            val engine = mockEngine("/getSundryDayInfo_no.json")
            val application = startKoin {
                modules(fakeHolidayClientModule(engine))
            }
            val dataSource = application.koin.get<SpecialDayRemoteDataSource>()
            val response = dataSource.get(fixture.giveMeOne())

            response.shouldBeEmpty()

            stopKoin()
        }

        test("getSundryDayInfo_one.json") {
            val engine = mockEngine("/getSundryDayInfo_one.json")
            val application = startKoin {
                modules(fakeHolidayClientModule(engine))
            }
            val dataSource = application.koin.get<SpecialDayRemoteDataSource>()
            val response = dataSource.get(fixture.giveMeOne())

            response shouldHaveSize 1
            response.first() shouldBe HolidayRemoteEntity(
                name = "단오",
                localDate = LocalDate(2024, 6, 10),
            )

            stopKoin()
        }

        test("getSundryDayInfo_many.json") {
            val engine = mockEngine("/getSundryDayInfo_many.json")
            val application = startKoin {
                modules(fakeHolidayClientModule(engine))
            }
            val dataSource = application.koin.get<SpecialDayRemoteDataSource>()
            val response = dataSource.get(fixture.giveMeOne())

            response shouldHaveSize 2
            response.first() shouldBe HolidayRemoteEntity(
                name = "칠석",
                localDate = LocalDate(2024, 8, 10),
            )

            stopKoin()
        }
    }
}
