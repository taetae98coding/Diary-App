package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.github.taetae98coding.diary.core.holiday.database.entity.SpecialDayLocalEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SpecialDayMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Local to Entity") {
            val local = fixture.giveMeOne<SpecialDayLocalEntity>()
            val entity = local.toEntity()

            entity.name shouldBe local.name
            entity.dateRange.start shouldBe local.localDate
            entity.dateRange.endInclusive shouldBe local.localDate
        }

        test("Remote to Local SpecialDay") {
            val remote = fixture.giveMeOne<HolidayRemoteEntity>()
            val local = remote.toLocalSpecialDayEntity()

            local.name shouldBe remote.name
            local.localDate shouldBe remote.localDate
        }
    }
}
