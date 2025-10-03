package io.github.taetae98coding.diary.domain.credentials.usecase

import io.github.taetae98coding.diary.domain.credentials.repository.CredentialsRepository
import io.github.taetae98coding.diary.domain.push.usecase.RegisterFcmPushTokenUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.mockk

class LogoutUseCaseTest : BehaviorSpec() {
    init {
        val registerFcmPushTokenUseCase = mockk<RegisterFcmPushTokenUseCase>(relaxed = true, relaxUnitFun = true)
        val credentialsRepository = mockk<CredentialsRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = LogoutUseCase(
            registerFcmPushTokenUseCase = registerFcmPushTokenUseCase,
            credentialsRepository = credentialsRepository,
        )

        Given("로그아웃하는 경우") {
            When("유즈케이스를 실행하면") {
                val result = useCase()

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("세션 삭제와 푸시토큰 등록이 호출된다") {
                    coVerify(exactly = 1) { credentialsRepository.delete() }
                    coVerify(exactly = 1) { registerFcmPushTokenUseCase() }
                }
            }
        }
    }
}
