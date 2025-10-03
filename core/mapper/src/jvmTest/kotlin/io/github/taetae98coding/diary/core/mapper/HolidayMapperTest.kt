package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.github.taetae98coding.diary.core.holiday.database.entity.HolidayLocalEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class HolidayMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Local to Entity") {
            val local = fixture.giveMeOne<HolidayLocalEntity>()
            val entity = local.toEntity()

            entity.dateRange.start shouldBe local.localDate
            entity.dateRange.endInclusive shouldBe local.localDate
        }

        test("Local.title to Entity.title") {
            listOf(
                "1월1일" to "새해",
                "기독탄신일" to "크리스마스",
            ).forEach { (actual, expected) ->
                val local = fixture.giveMeKotlinBuilder<HolidayLocalEntity>()
                    .set(HolidayLocalEntity::name, actual)
                    .sample()

                local.toEntity().name shouldBe expected
            }
        }

        test("Remote to Local") {
            val remote = fixture.giveMeOne<HolidayRemoteEntity>()
            val local = remote.toLocal()

            local.name shouldBe remote.name
            local.localDate shouldBe remote.localDate
        }
    }
}
