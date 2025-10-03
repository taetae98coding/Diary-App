package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.MemoDetailLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate

class MemoMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Entity to Local") {
            val entity = fixture.giveMeOne<Memo>()
            val local = entity.toLocal()

            local.id shouldBe entity.id
            local.detail shouldBe entity.detail.toLocal()
            local.primaryTag shouldBe entity.primaryTag
            local.isFinished shouldBe entity.isFinished
            local.isDeleted shouldBe entity.isDeleted
            local.createdAt shouldBe entity.createdAt
            local.updatedAt shouldBe entity.updatedAt
            local.serverUpdatedAt shouldBe -1L

            entity shouldBe local.toEntity()
        }

        test("Local to Entity dateRange null") {
            val local = fixture.giveMeKotlinBuilder<MemoLocalEntity>()
                .set(
                    MemoLocalEntity::detail,
                    fixture.giveMeKotlinBuilder<MemoDetailLocalEntity>()
                        .set(MemoDetailLocalEntity::start, null)
                        .set(MemoDetailLocalEntity::endInclusive, null)
                        .sample(),
                )
                .sample()
            val entity = local.toEntity()

            entity.id shouldBe local.id
            entity.detail shouldBe local.detail.toEntity()
            entity.primaryTag shouldBe local.primaryTag
            entity.isFinished shouldBe local.isFinished
            entity.isDeleted shouldBe local.isDeleted
            entity.createdAt shouldBe local.createdAt
            entity.updatedAt shouldBe local.updatedAt

            local shouldBe entity.toLocal().copy(serverUpdatedAt = local.serverUpdatedAt)
        }

        test("Local to Entity dateRange not null") {
            val local = fixture.giveMeKotlinBuilder<MemoLocalEntity>()
                .set(
                    MemoLocalEntity::detail,
                    fixture.giveMeKotlinBuilder<MemoDetailLocalEntity>()
                        .set(MemoDetailLocalEntity::start, fixture.giveMeOne<LocalDate>())
                        .set(MemoDetailLocalEntity::endInclusive, fixture.giveMeOne<LocalDate>())
                        .sample(),
                )
                .sample()
            val entity = local.toEntity()

            entity.id shouldBe local.id
            entity.detail shouldBe local.detail.toEntity()
            entity.primaryTag shouldBe local.primaryTag
            entity.isFinished shouldBe local.isFinished
            entity.isDeleted shouldBe local.isDeleted
            entity.createdAt shouldBe local.createdAt
            entity.updatedAt shouldBe local.updatedAt

            local shouldBe entity.toLocal().copy(serverUpdatedAt = local.serverUpdatedAt)
        }

        test("Local to Remote") {
            val local = fixture.giveMeOne<MemoLocalEntity>()
            val remote = local.toRemote()

            remote.id shouldBe local.id
            remote.detail shouldBe local.detail.toRemote()
            remote.primaryTag shouldBe local.primaryTag
            remote.isFinished shouldBe local.isFinished
            remote.isDeleted shouldBe local.isDeleted
            remote.createdAt shouldBe local.createdAt
            remote.updatedAt shouldBe local.updatedAt

            local shouldBe remote.toLocal().copy(serverUpdatedAt = local.serverUpdatedAt)
        }

        test("Remote to Local") {
            val remote = fixture.giveMeOne<MemoRemoteEntity>()
            val local = remote.toLocal()

            local.id shouldBe remote.id
            local.detail shouldBe remote.detail.toLocal()
            local.primaryTag shouldBe remote.primaryTag
            local.isFinished shouldBe remote.isFinished
            local.isDeleted shouldBe remote.isDeleted
            local.createdAt shouldBe remote.createdAt
            local.updatedAt shouldBe remote.updatedAt
            local.serverUpdatedAt shouldBe remote.updatedAt

            remote shouldBe local.toRemote()
        }
    }
}
