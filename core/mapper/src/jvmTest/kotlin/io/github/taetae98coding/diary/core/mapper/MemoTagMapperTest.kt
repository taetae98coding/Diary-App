package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MemoTagMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Local to Remote") {
            val local = fixture.giveMeOne<MemoTagLocalEntity>()
            val remote = local.toRemote()

            remote.memoId shouldBe local.memoId
            remote.tagId shouldBe local.tagId
            remote.isMemoTag shouldBe local.isMemoTag
            remote.updatedAt shouldBe local.updatedAt

            local shouldBe remote.toLocal().copy(serverUpdatedAt = local.serverUpdatedAt)
        }

        test("Remote to Local") {
            val remote = fixture.giveMeOne<MemoTagRemoteEntity>()
            val local = remote.toLocal()

            local.memoId shouldBe remote.memoId
            local.tagId shouldBe remote.tagId
            local.isMemoTag shouldBe remote.isMemoTag
            local.updatedAt shouldBe remote.updatedAt
            local.serverUpdatedAt shouldBe remote.updatedAt

            remote shouldBe local.toRemote()
        }
    }
}
