package io.github.taetae98coding.diary.domain.tag.usecase

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import io.github.taetae98coding.diary.domain.tag.repository.AccountTagRepository
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class AddTagUseCase internal constructor(
    private val clock: Clock,
    private val checkTagDetailUseCase: CheckTagDetailUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val requestDiaryBackupUseCase: RequestDiaryBackupUseCase,
    private val accountTagRepository: AccountTagRepository,
) {
    public suspend operator fun invoke(
        detail: TagDetail,
    ): Result<Unit> {
        return runCatching {
            checkTagDetailUseCase(detail).getOrThrow()

            val account = getAccountUseCase().first().getOrThrow()
            val createdAt = clock.now().toEpochMilliseconds()
            val tag = Tag(
                id = Uuid.random(),
                detail = detail,
                isFinished = false,
                isDeleted = false,
                updatedAt = createdAt,
                createdAt = createdAt,
            )

            accountTagRepository.add(account, tag)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> requestDiaryBackupUseCase()
            }
        }
    }
}
