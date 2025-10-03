package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BuddyGroupMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Local to Entity") {
            val local = fixture.giveMeOne<BuddyGroupLocalEntity>()
            val entity = local.toEntity()

            entity.id shouldBe local.id
            entity.detail shouldBe local.detail.toEntity()
        }

        test("Remote to Local") {
            val remote = fixture.giveMeOne<BuddyGroupRemoteEntity>()
            val local = remote.toLocal()

            local.id shouldBe remote.id
            local.detail shouldBe remote.detail.toLocal()
        }
    }
}
