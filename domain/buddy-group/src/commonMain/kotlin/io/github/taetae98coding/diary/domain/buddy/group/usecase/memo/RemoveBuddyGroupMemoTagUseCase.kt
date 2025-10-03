package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoTagRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class RemoveBuddyGroupMemoTagUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupMemoTagRepository: BuddyGroupMemoTagRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, memoId: Uuid, tagId: Uuid): Result<Unit> {
        return runCatching {
            return runCatching {
                when (val account = getAccountUseCase().first().getOrThrow()) {
                    is Account.Guest -> throw NotLoginException()
                    is Account.User -> buddyGroupMemoTagRepository.updateMemoTag(account, buddyGroupId, memoId, tagId, false)
                }
            }
        }
    }
}
