package io.github.taetae98coding.diary.domain.push.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.push.repository.FcmPushTokenRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class RegisterFcmPushTokenUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val repository = mockk<FcmPushTokenRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = RegisterFcmPushTokenUseCase(
            getAccountUseCase = getAccountUseCase,
            fcmPushTokenRepository = repository,
        )

        Given("FCM 토큰을 등록하는 경우") {
            val account = fixture.giveMeOne<Account>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase()

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("레포지토리를 호출한다") {
                    coVerify(exactly = 1) { repository.register(account) }
                }
            }
        }
    }
}
