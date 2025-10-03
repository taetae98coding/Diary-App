package io.github.taetae98coding.diary.domain.credentials.usecase

import io.github.taetae98coding.diary.domain.credentials.repository.CredentialsRepository
import io.github.taetae98coding.diary.domain.push.usecase.RegisterFcmPushTokenUseCase
import io.github.taetae98coding.diary.domain.sync.usecase.RequestSyncUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
public class LoginByGoogleUseCase internal constructor(
    private val requestSyncUseCase: RequestSyncUseCase,
    private val registerFcmPushTokenUseCase: RegisterFcmPushTokenUseCase,
    private val credentialsRepository: CredentialsRepository,
) {
    public suspend operator fun invoke(idToken: String): Result<Unit> {
        return runCatching {
            credentialsRepository.updateGoogleSession(idToken)
            coroutineScope {
                launch { requestSyncUseCase() }
                launch { registerFcmPushTokenUseCase() }
            }
        }
    }
}
