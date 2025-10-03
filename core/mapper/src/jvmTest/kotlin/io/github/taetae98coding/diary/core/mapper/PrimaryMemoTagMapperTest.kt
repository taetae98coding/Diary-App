package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.PrimaryMemoTagLocalEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PrimaryMemoTagMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Local to Entity") {
            val local = fixture.giveMeOne<PrimaryMemoTagLocalEntity>()
            val entity = local.toEntity()

            entity.isPrimary shouldBe local.isPrimary
            entity.tag shouldBe local.tag.toEntity()
        }
    }
}
