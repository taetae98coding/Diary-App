package io.github.taetae98coding.diary.domain.buddy.group.usecase.tag

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupTagRepository
import io.github.taetae98coding.diary.domain.tag.usecase.GetTagUseCase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class UpdateBuddyGroupTagUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val getTagUseCase: GetTagUseCase,
    private val buddyGroupTagRepository: BuddyGroupTagRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, tagId: Uuid, detail: TagDetail): Result<Unit> {
        return runCatching {
            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> update(account, buddyGroupId, tagId, detail)
            }
        }
    }

    private suspend fun update(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, detail: TagDetail) {
        val tag = getTagUseCase(tagId).first().getOrThrow() ?: return
        val detail = detail.copy(
            title = detail.title.ifBlank { tag.detail.title },
        )

        buddyGroupTagRepository.update(account, buddyGroupId, tagId, detail)
    }
}
