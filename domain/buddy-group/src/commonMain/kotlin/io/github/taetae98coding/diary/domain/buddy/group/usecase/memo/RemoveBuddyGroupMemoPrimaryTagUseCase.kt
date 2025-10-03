package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class RemoveBuddyGroupMemoPrimaryTagUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupMemoRepository: BuddyGroupMemoRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, memoId: Uuid): Result<Unit> {
        return runCatching {
            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> Unit
                is Account.User -> buddyGroupMemoRepository.updatePrimaryTag(account, buddyGroupId, memoId, null)
            }
        }
    }
}
