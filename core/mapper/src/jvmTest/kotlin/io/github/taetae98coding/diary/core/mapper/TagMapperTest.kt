package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TagMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Entity to Local") {
            val entity = fixture.giveMeOne<Tag>()
            val local = entity.toLocal()

            local.id shouldBe entity.id
            local.detail shouldBe entity.detail.toLocal()
            local.isFinished shouldBe entity.isFinished
            local.isDeleted shouldBe entity.isDeleted
            local.createdAt shouldBe entity.createdAt
            local.updatedAt shouldBe entity.updatedAt
            local.serverUpdatedAt shouldBe -1L

            entity shouldBe local.toEntity()
        }

        test("Local to Entity") {
            val local = fixture.giveMeOne<TagLocalEntity>()
            val entity = local.toEntity()

            entity.id shouldBe local.id
            entity.detail shouldBe local.detail.toEntity()
            entity.isFinished shouldBe local.isFinished
            entity.isDeleted shouldBe local.isDeleted
            entity.createdAt shouldBe local.createdAt
            entity.updatedAt shouldBe local.updatedAt

            local shouldBe entity.toLocal().copy(serverUpdatedAt = local.serverUpdatedAt)
        }

        test("Local to Remote") {
            val local = fixture.giveMeOne<TagLocalEntity>()
            val remote = local.toRemote()

            remote.id shouldBe local.id
            remote.detail shouldBe local.detail.toRemote()
            remote.isFinished shouldBe local.isFinished
            remote.isDeleted shouldBe local.isDeleted
            remote.createdAt shouldBe local.createdAt
            remote.updatedAt shouldBe local.updatedAt

            local shouldBe remote.toLocal().copy(serverUpdatedAt = local.serverUpdatedAt)
        }

        test("Remote to Local") {
            val remote = fixture.giveMeOne<TagRemoteEntity>()
            val local = remote.toLocal()

            local.id shouldBe remote.id
            local.detail shouldBe remote.detail.toLocal()
            local.isFinished shouldBe remote.isFinished
            local.isDeleted shouldBe remote.isDeleted
            local.createdAt shouldBe remote.createdAt
            local.updatedAt shouldBe remote.updatedAt
            local.serverUpdatedAt shouldBe remote.updatedAt

            remote shouldBe local.toRemote()
        }
    }
}
