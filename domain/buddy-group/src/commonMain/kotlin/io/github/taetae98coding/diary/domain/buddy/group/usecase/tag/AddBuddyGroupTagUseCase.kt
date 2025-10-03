package io.github.taetae98coding.diary.domain.buddy.group.usecase.tag

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupTagRepository
import io.github.taetae98coding.diary.domain.tag.usecase.CheckTagDetailUseCase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class AddBuddyGroupTagUseCase internal constructor(
    private val checkTagDetailUseCase: CheckTagDetailUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupTagRepository: BuddyGroupTagRepository,
) {
    public suspend operator fun invoke(
        buddyGroupId: Uuid,
        detail: TagDetail,
    ): Result<Unit> {
        return runCatching {
            checkTagDetailUseCase(detail).getOrThrow()

            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> buddyGroupTagRepository.add(account, buddyGroupId, detail)
            }
        }
    }
}
