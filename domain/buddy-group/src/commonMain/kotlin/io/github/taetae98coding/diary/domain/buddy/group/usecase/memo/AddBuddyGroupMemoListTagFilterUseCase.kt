package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoListTagFilterRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class AddBuddyGroupMemoListTagFilterUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupMemoListTagFilterRepository: BuddyGroupMemoListTagFilterRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, tagId: Uuid): Result<Unit> {
        return runCatching {
            when (getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> buddyGroupMemoListTagFilterRepository.upsert(buddyGroupId, tagId, true)
            }
        }
    }
}
