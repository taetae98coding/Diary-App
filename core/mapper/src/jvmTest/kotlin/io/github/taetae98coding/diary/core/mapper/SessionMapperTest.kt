package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.preferences.entity.SessionPreferencesEntity
import io.github.taetae98coding.diary.core.service.entity.account.SessionRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SessionMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Preferences to Entity") {
            val preferences = fixture.giveMeOne<SessionPreferencesEntity>()
            val entity = preferences.toEntity()

            entity.token shouldBe preferences.token
            entity.id shouldBe preferences.id
            entity.email shouldBe preferences.email
            entity.profileImage shouldBe preferences.profileImage
        }

        test("Remote to Preferences") {
            val remote = fixture.giveMeOne<SessionRemoteEntity>()
            val preferences = remote.toPreferences()

            preferences.token shouldBe remote.token
            preferences.id shouldBe remote.account.id
            preferences.email shouldBe remote.account.email
            preferences.profileImage shouldBe remote.account.profileImage
        }
    }
}
