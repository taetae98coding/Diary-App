package io.github.taetae98coding.diary.domain.memo.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class AddMemoUseCase internal constructor(
    private val clock: Clock,
    private val checkMemoDetailUseCase: CheckMemoDetailUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val requestDiaryBackupUseCase: RequestDiaryBackupUseCase,
    private val accountMemoRepository: AccountMemoRepository,
) {
    public suspend operator fun invoke(
        detail: MemoDetail,
        primaryTag: Uuid?,
        memoTagIds: Set<Uuid>,
    ): Result<Unit> {
        return runCatching {
            checkMemoDetailUseCase(detail).getOrThrow()

            val account = getAccountUseCase().first().getOrThrow()
            val createdAt = clock.now().toEpochMilliseconds()
            val memo = Memo(
                id = Uuid.random(),
                detail = detail,
                primaryTag = primaryTag,
                isFinished = false,
                isDeleted = false,
                updatedAt = createdAt,
                createdAt = createdAt,
            )

            accountMemoRepository.add(account, memo, memoTagIds)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> requestDiaryBackupUseCase()
            }
        }
    }
}
