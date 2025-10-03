package io.github.taetae98coding.diary.domain.buddy.group.usecase.tag

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupTagRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class FinishBuddyGroupTagUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupTagRepository: BuddyGroupTagRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, tagId: Uuid): Result<Unit> {
        return runCatching {
            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> buddyGroupTagRepository.updateFinished(account, buddyGroupId, tagId, true)
            }
        }
    }
}
