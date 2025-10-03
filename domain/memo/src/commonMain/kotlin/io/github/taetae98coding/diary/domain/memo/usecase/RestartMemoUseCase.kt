package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class RestartMemoUseCase internal constructor(
    private val requestDiaryBackupUseCase: RequestDiaryBackupUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val accountMemoRepository: AccountMemoRepository,
) {
    public suspend operator fun invoke(memoId: Uuid): Result<Unit> {
        return runCatching {
            val account = getAccountUseCase().first().getOrThrow()

            accountMemoRepository.updateFinished(account, memoId, false)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> requestDiaryBackupUseCase()
            }
        }
    }
}
