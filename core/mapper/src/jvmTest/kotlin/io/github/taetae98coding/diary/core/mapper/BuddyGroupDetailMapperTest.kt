package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupDetailLocalEntity
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupDetailRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BuddyGroupDetailMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Entity to Local") {
            val entity = fixture.giveMeOne<BuddyGroupDetail>()
            val local = entity.toLocal()

            local.title shouldBe entity.title
            local.description shouldBe entity.description

            entity shouldBe local.toEntity()
        }

        test("Entity to Remote") {
            val entity = fixture.giveMeOne<BuddyGroupDetail>()
            val remote = entity.toRemote()

            remote.title shouldBe entity.title
            remote.description shouldBe entity.description
        }

        test("Local to Entity") {
            val local = fixture.giveMeOne<BuddyGroupDetailLocalEntity>()
            val entity = local.toEntity()

            entity.title shouldBe local.title
            entity.description shouldBe local.description

            local shouldBe entity.toLocal()
        }

        test("Remote to Local") {
            val remote = fixture.giveMeOne<BuddyGroupDetailRemoteEntity>()
            val local = remote.toLocal()

            local.title shouldBe remote.title
            local.description shouldBe remote.description
        }
    }
}
