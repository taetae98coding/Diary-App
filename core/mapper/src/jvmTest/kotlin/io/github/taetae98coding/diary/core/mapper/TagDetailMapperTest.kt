package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.TagDetailLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.service.entity.tag.TagDetailRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TagDetailMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Entity to Local") {
            val entity = fixture.giveMeOne<TagDetail>()
            val local = entity.toLocal()

            local.title shouldBe entity.title
            local.description shouldBe entity.description
            local.color shouldBe entity.color

            entity shouldBe local.toEntity()
        }

        test("Entity to Remote") {
            val entity = fixture.giveMeOne<TagDetail>()
            val remote = entity.toRemote()

            remote.title shouldBe entity.title
            remote.description shouldBe entity.description
            remote.color shouldBe entity.color
        }

        test("Local to Entity") {
            val local = fixture.giveMeOne<TagDetailLocalEntity>()
            val entity = local.toEntity()

            entity.title shouldBe local.title
            entity.description shouldBe local.description
            entity.color shouldBe local.color

            local shouldBe entity.toLocal()
        }

        test("Local to Remote") {
            val local = fixture.giveMeOne<TagDetailLocalEntity>()
            val remote = local.toRemote()

            remote.title shouldBe local.title
            remote.description shouldBe local.description
            remote.color shouldBe local.color

            local shouldBe remote.toLocal()
        }

        test("Remote to Local") {
            val remote = fixture.giveMeOne<TagDetailRemoteEntity>()
            val local = remote.toLocal()

            local.title shouldBe remote.title
            local.description shouldBe remote.description
            local.color shouldBe remote.color

            remote shouldBe local.toRemote()
        }
    }
}
