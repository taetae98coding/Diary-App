package io.github.taetae98coding.diary.domain.tag.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.tag.repository.TagRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class FetchTagUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val tagRepository: TagRepository,
) {
    public suspend operator fun invoke(tagId: Uuid): Result<Unit> {
        return runCatching {
            when (val account = getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> tagRepository.fetch(account, tagId)
            }
        }
    }
}
