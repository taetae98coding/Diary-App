package io.github.taetae98coding.diary.domain.push.usecase

import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.push.repository.FcmPushTokenRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class RegisterFcmPushTokenUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val fcmPushTokenRepository: FcmPushTokenRepository,
) {
    public suspend operator fun invoke(): Result<Unit> {
        return runCatching { fcmPushTokenRepository.register(getAccountUseCase().first().getOrThrow()) }
    }
}
