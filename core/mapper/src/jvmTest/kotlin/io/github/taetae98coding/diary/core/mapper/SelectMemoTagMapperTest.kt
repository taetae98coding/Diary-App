package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SelectMemoTagMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Local to Entity") {
            val local = fixture.giveMeOne<SelectMemoTagLocalEntity>()
            val entity = local.toEntity()

            entity.isSelected shouldBe local.isSelected
            entity.tag shouldBe local.tag.toEntity()
        }
    }
}
