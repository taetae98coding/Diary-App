package io.github.taetae98coding.diary.core.mapper

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherTypeRemoteEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class WeatherTypeMapperTest : FunSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        test("Remote to Entity") {
            val remote = fixture.giveMeOne<WeatherTypeRemoteEntity>()
            val entity = remote.toEntity()

            entity.icon shouldBe remote.icon
            entity.description shouldBe remote.description
        }
    }
}
