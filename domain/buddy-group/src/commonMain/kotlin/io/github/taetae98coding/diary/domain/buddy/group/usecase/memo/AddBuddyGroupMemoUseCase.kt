package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoRepository
import io.github.taetae98coding.diary.domain.memo.usecase.CheckMemoDetailUseCase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class AddBuddyGroupMemoUseCase internal constructor(
    private val checkMemoDetailUseCase: CheckMemoDetailUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupMemoRepository: BuddyGroupMemoRepository,
) {
    public suspend operator fun invoke(
        buddyGroupId: Uuid,
        detail: MemoDetail,
        primaryTag: Uuid?,
        memoTagIds: Set<Uuid>,
    ): Result<Unit> {
        return runCatching {
            checkMemoDetailUseCase(detail).getOrThrow()

            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> buddyGroupMemoRepository.add(account, buddyGroupId, detail, primaryTag, memoTagIds)
            }
        }
    }
}
