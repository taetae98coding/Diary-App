package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class RemoveMemoPrimaryTagUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val requestDiaryBackupUseCase: RequestDiaryBackupUseCase,
    private val accountMemoRepository: AccountMemoRepository,
) {
    public suspend operator fun invoke(memoId: Uuid): Result<Unit> {
        return runCatching {
            val account = getAccountUseCase().first().getOrThrow()

            accountMemoRepository.updatePrimaryTag(account, memoId, null)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> requestDiaryBackupUseCase()
            }
        }
    }
}
