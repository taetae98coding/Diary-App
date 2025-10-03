package io.github.taetae98coding.diary.domain.sync.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.sync.repository.DiarySyncRepository
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class SyncUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val syncRepository: DiarySyncRepository,
) {
    public suspend operator fun invoke(): Result<Unit> {
        return runCatching {
            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> syncRepository.sync(account)
            }
        }
    }
}
