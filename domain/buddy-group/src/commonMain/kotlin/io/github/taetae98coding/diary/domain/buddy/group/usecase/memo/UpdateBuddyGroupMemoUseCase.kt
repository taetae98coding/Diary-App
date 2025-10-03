package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoRepository
import io.github.taetae98coding.diary.domain.memo.usecase.GetMemoUseCase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class UpdateBuddyGroupMemoUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val getMemoUseCase: GetMemoUseCase,
    private val buddyGroupMemoRepository: BuddyGroupMemoRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, memoId: Uuid, detail: MemoDetail): Result<Unit> {
        return runCatching {
            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> update(account, buddyGroupId, memoId, detail)
            }
        }
    }

    private suspend fun update(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, detail: MemoDetail) {
        val memo = getMemoUseCase(memoId).first().getOrThrow() ?: return
        val detail = detail.copy(
            title = detail.title.ifBlank { memo.detail.title },
        )

        buddyGroupMemoRepository.update(account, buddyGroupId, memoId, detail)
    }
}
