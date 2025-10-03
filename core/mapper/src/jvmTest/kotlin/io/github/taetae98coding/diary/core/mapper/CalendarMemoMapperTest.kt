package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoLocalEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CalendarMemoMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Local to Entity") {
            val local = fixture.giveMeOne<CalendarMemoLocalEntity>()
            val entity = local.toEntity()

            entity.id shouldBe local.id
            entity.title shouldBe local.title
            entity.dateRange.start shouldBe local.start
            entity.dateRange.endInclusive shouldBe local.endInclusive
            entity.color shouldBe local.color
        }
    }
}
