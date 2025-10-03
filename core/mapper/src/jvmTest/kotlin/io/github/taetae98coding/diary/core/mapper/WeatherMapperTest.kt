package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherRemoteEntity
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherTypeRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class WeatherMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Remote to Entity") {
            val remote = fixture.giveMeOne<WeatherRemoteEntity>()
            val entity = remote.toEntity()

            entity.type shouldBe remote.type.map(WeatherTypeRemoteEntity::toEntity)
            entity.temperature shouldBe remote.main.temperature
            entity.instant shouldBe remote.instant
        }
    }
}
