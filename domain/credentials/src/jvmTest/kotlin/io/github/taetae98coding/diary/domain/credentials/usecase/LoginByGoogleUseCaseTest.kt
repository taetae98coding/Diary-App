package io.github.taetae98coding.diary.domain.credentials.usecase

import io.github.taetae98coding.diary.domain.credentials.repository.CredentialsRepository
import io.github.taetae98coding.diary.domain.push.usecase.RegisterFcmPushTokenUseCase
import io.github.taetae98coding.diary.domain.sync.usecase.RequestSyncUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.mockk

class LoginByGoogleUseCaseTest : BehaviorSpec() {
    init {
        val requestSyncUseCase = mockk<RequestSyncUseCase>(relaxed = true, relaxUnitFun = true)
        val registerFcmPushTokenUseCase = mockk<RegisterFcmPushTokenUseCase>(relaxed = true, relaxUnitFun = true)
        val credentialsRepository = mockk<CredentialsRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = LoginByGoogleUseCase(
            requestSyncUseCase = requestSyncUseCase,
            registerFcmPushTokenUseCase = registerFcmPushTokenUseCase,
            credentialsRepository = credentialsRepository,
        )

        Given("구글 토큰으로 로그인하는 경우") {
            val idToken = "token"

            When("유즈케이스를 실행하면") {
                val result = useCase(idToken)

                Then("성공한다") { result.shouldBeSuccess() }
                Then("세션 업데이트, 동기화, 푸시토큰 등록이 호출된다") {
                    coVerify(exactly = 1) { credentialsRepository.updateGoogleSession(idToken) }
                    coVerify(exactly = 1) { requestSyncUseCase() }
                    coVerify(exactly = 1) { registerFcmPushTokenUseCase() }
                }
            }
        }
    }
}
