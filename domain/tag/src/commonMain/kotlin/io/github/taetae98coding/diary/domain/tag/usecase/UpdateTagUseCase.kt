package io.github.taetae98coding.diary.domain.tag.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import io.github.taetae98coding.diary.domain.tag.exception.TagNotFoundException
import io.github.taetae98coding.diary.domain.tag.repository.AccountTagRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class UpdateTagUseCase internal constructor(
    private val requestDiaryBackupUseCase: RequestDiaryBackupUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getTagUseCase: GetTagUseCase,
    private val accountTagRepository: AccountTagRepository,
) {
    public suspend operator fun invoke(tagId: Uuid, detail: TagDetail): Result<Unit> {
        return runCatching {
            val account = getAccountUseCase().first().getOrThrow()
            val tag = getTagUseCase(tagId).first().getOrThrow() ?: throw TagNotFoundException()
            val detail = detail.copy(
                title = detail.title.ifBlank { tag.detail.title },
            )

            accountTagRepository.update(account, tagId, detail)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> requestDiaryBackupUseCase()
            }
        }
    }
}
