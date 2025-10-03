package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.MemoDetailLocalEntity
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.service.entity.memo.MemoDetailRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate

class MemoDetailMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Entity to Local") {
            val entity = fixture.giveMeOne<MemoDetail>()
            val local = entity.toLocal()

            local.title shouldBe entity.title
            local.description shouldBe entity.description
            local.color shouldBe entity.color

            entity shouldBe local.toEntity()
        }

        test("Entity to Remote") {
            val entity = fixture.giveMeOne<MemoDetail>()
            val remote = entity.toRemote()

            remote.title shouldBe entity.title
            remote.description shouldBe entity.description
            remote.start shouldBe entity.dateRange?.start
            remote.endInclusive shouldBe entity.dateRange?.endInclusive
            remote.color shouldBe entity.color
        }

        test("Local to Entity dateRange null") {
            val local = fixture.giveMeKotlinBuilder<MemoDetailLocalEntity>()
                .set(MemoDetailLocalEntity::start, null)
                .set(MemoDetailLocalEntity::endInclusive, null)
                .sample()
            val entity = local.toEntity()

            entity.title shouldBe local.title
            entity.description shouldBe local.description
            entity.dateRange?.start shouldBe local.start
            entity.dateRange?.endInclusive shouldBe local.endInclusive
            entity.color shouldBe local.color

            local shouldBe entity.toLocal()
        }

        test("Local to Entity dateRange not null") {
            val local = fixture.giveMeKotlinBuilder<MemoDetailLocalEntity>()
                .set(MemoDetailLocalEntity::start, fixture.giveMeOne<LocalDate>())
                .set(MemoDetailLocalEntity::endInclusive, fixture.giveMeOne<LocalDate>())
                .sample()
            val entity = local.toEntity()

            entity.title shouldBe local.title
            entity.description shouldBe local.description
            entity.dateRange?.start shouldBe local.start
            entity.dateRange?.endInclusive shouldBe local.endInclusive
            entity.color shouldBe local.color

            local shouldBe entity.toLocal()
        }

        test("Local to Remote") {
            val local = fixture.giveMeOne<MemoDetailLocalEntity>()
            val remote = local.toRemote()

            remote.title shouldBe local.title
            remote.description shouldBe local.description
            remote.start shouldBe local.start
            remote.endInclusive shouldBe local.endInclusive
            remote.color shouldBe local.color

            local shouldBe remote.toLocal()
        }

        test("Remote to Local") {
            val remote = fixture.giveMeOne<MemoDetailRemoteEntity>()
            val local = remote.toLocal()

            local.title shouldBe remote.title
            local.description shouldBe remote.description
            local.start shouldBe remote.start
            local.endInclusive shouldBe remote.endInclusive
            local.color shouldBe remote.color

            remote shouldBe local.toRemote()
        }
    }
}
