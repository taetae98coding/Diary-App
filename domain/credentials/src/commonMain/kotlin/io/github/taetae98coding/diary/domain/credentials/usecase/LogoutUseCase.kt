package io.github.taetae98coding.diary.domain.credentials.usecase

import io.github.taetae98coding.diary.domain.credentials.repository.CredentialsRepository
import io.github.taetae98coding.diary.domain.push.usecase.RegisterFcmPushTokenUseCase
import org.koin.core.annotation.Factory

@Factory
public class LogoutUseCase internal constructor(
    private val registerFcmPushTokenUseCase: RegisterFcmPushTokenUseCase,
    private val credentialsRepository: CredentialsRepository,
) {
    public suspend operator fun invoke(): Result<Unit> {
        return runCatching {
            credentialsRepository.delete()
            registerFcmPushTokenUseCase()
        }
    }
}
