package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BuddyMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Remote to Entity") {
            val remote = fixture.giveMeOne<BuddyRemoteEntity>()
            val entity = remote.toEntity()

            entity.id shouldBe remote.id
            entity.email shouldBe remote.email
            entity.profileImage shouldBe remote.profileImage
        }
    }
}
