package io.github.taetae98coding.diary.data.location.repository

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.location.Location
import io.github.taetae98coding.diary.core.ip.service.datasource.IpRemoteDataSource
import io.github.taetae98coding.diary.core.ip.service.entity.LocationRemoteEntity
import io.github.taetae98coding.diary.core.location.LocationManager
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first

class LocationRepositoryImplTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val ipRemoteDataSource = mockk<IpRemoteDataSource>(relaxed = true, relaxUnitFun = true)
        val locationManager = mockk<LocationManager>(relaxed = true, relaxUnitFun = true)
        val repository = LocationRepositoryImpl(
            ipRemoteDataSource = ipRemoteDataSource,
            locationManager = locationManager,
        )

        Given("디바이스 위치가 null인 경우") {
            val locationRemoteEntity = fixture.giveMeOne<LocationRemoteEntity>()

            coEvery { locationManager.getLastLocation() } returns null
            coEvery { ipRemoteDataSource.get() } returns locationRemoteEntity

            When("get을 호출하면") {
                val result = repository.get().first()

                Then("IP 기반 위치를 반환한다") {
                    result shouldBe locationRemoteEntity.toEntity()
                }
            }
        }

        Given("디바이스 위치가 존재하는 경우") {
            val deviceLocation = fixture.giveMeOne<Location>()

            coEvery { locationManager.getLastLocation() } returns deviceLocation

            When("get을 호출하면") {
                val result = repository.get().first()

                Then("디바이스 위치를 반환한다") {
                    result shouldBe deviceLocation
                }
            }
        }
    }
}
