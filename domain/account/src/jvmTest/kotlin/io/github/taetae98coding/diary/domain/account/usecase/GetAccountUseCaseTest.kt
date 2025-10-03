package io.github.taetae98coding.diary.domain.account.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.account.Session
import io.github.taetae98coding.diary.domain.account.repository.SessionRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetAccountUseCaseTest : BehaviorSpec() {
    init {
        val sessionRepository = mockk<SessionRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetAccountUseCase(
            sessionRepository = sessionRepository,
        )

        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        Given("세션이 null로 조회되는 경우") {
            every { sessionRepository.get() } returns flowOf(null)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("Account.Guest 를 방출한다") {
                    result.shouldBeSuccess()
                        .shouldBeInstanceOf<Account.Guest>()
                }
            }
        }

        Given("세션이 존재하는 경우") {
            val session = fixture.giveMeOne<Session>()
            every { sessionRepository.get() } returns flowOf(session)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Account.User 로 매핑되어 방출한다") {
                    val account = result.shouldBeSuccess()
                        .shouldBeInstanceOf<Account.User>()

                    account.token shouldBe session.token
                    account.id shouldBe session.id
                    account.email shouldBe session.email
                    account.profileImage shouldBe session.profileImage
                }
            }
        }
    }
}
