package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.ip.service.entity.LocationRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LocationMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Remote to Entity") {
            val remote = fixture.giveMeOne<LocationRemoteEntity>()
            val entity = remote.toEntity()

            entity.latitude shouldBe remote.latitude
            entity.longitude shouldBe remote.longitude
        }
    }
}
