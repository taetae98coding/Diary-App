package io.github.taetae98coding.diary.domain.buddy.group.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class UpdateBuddyGroupUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupRepository: BuddyGroupRepository,
) {
    public suspend operator fun invoke(
        buddyGroupId: Uuid,
        detail: BuddyGroupDetail,
    ): Result<Unit> {
        return runCatching {
            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> {
                    val group = requireNotNull(buddyGroupRepository.get(buddyGroupId).first())
                    val validatedDetail = detail.copy(title = detail.title.ifBlank { group.detail.title })

                    buddyGroupRepository.update(account, buddyGroupId, validatedDetail)
                }
            }
        }
    }
}
