package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.exception.MemoNotFoundException
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class UpdateMemoUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val requestDiaryBackupUseCase: RequestDiaryBackupUseCase,
    private val getMemoUseCase: GetMemoUseCase,
    private val accountMemoRepository: AccountMemoRepository,
) {
    public suspend operator fun invoke(memoId: Uuid, detail: MemoDetail): Result<Unit> {
        return runCatching {
            val account = getAccountUseCase().first().getOrThrow()
            val memo = getMemoUseCase(memoId).first().getOrThrow() ?: throw MemoNotFoundException()
            val detail = detail.copy(
                title = detail.title.ifBlank { memo.detail.title },
            )

            accountMemoRepository.update(account, memoId, detail)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> requestDiaryBackupUseCase()
            }
        }
    }
}
