package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoTagRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class SelectMemoTagUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val requestDiaryBackupUseCase: RequestDiaryBackupUseCase,
    private val accountMemoTagRepository: AccountMemoTagRepository,
) {
    public suspend operator fun invoke(memoId: Uuid, tagId: Uuid): Result<Unit> {
        return runCatching {
            val account = getAccountUseCase().first().getOrThrow()

            accountMemoTagRepository.updateMemoTag(account, memoId, tagId, true)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> requestDiaryBackupUseCase()
            }
        }
    }
}
