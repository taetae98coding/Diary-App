package io.github.taetae98coding.diary.domain.buddy.group.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.exception.BuddyEmptyException
import io.github.taetae98coding.diary.domain.buddy.group.exception.BuddyGroupTitleBlankException
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class AddBuddyGroupUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupRepository: BuddyGroupRepository,
) {
    public suspend operator fun invoke(detail: BuddyGroupDetail, buddyIds: Collection<Uuid>): Result<Unit> {
        return runCatching {
            if (detail.title.isBlank()) throw BuddyGroupTitleBlankException()
            if (buddyIds.isEmpty()) throw BuddyEmptyException()

            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> buddyGroupRepository.add(account, detail, buddyIds)
            }
        }
    }
}
