package io.github.taetae98coding.diary.domain.buddy.group.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetBuddyGroupUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val repository = mockk<BuddyGroupRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetBuddyGroupUseCase(
            buddyGroupRepository = repository,
        )

        Given("그룹이 존재하는 경우") {
            val groupId = fixture.giveMeOne<Uuid>()
            val group = fixture.giveMeOne<BuddyGroup>()
            every { repository.get(groupId) } returns flowOf(group)

            When("유즈케이스를 구독하면") {
                val result = useCase(groupId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(group)
                }
            }
        }

        Given("그룹이 존재하지 않는 경우") {
            val groupId = fixture.giveMeOne<Uuid>()
            every { repository.get(groupId) } returns flowOf(null)

            When("유즈케이스를 구독하면") {
                val result = useCase(groupId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(null)
                }
            }
        }
    }
}


