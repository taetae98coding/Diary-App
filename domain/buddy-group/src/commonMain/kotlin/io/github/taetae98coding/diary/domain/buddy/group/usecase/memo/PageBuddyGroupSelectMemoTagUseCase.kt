package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupSelectMemoTagRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class PageBuddyGroupSelectMemoTagUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupSelectMemoTagRepository: BuddyGroupSelectMemoTagRepository,
) {
    public operator fun invoke(buddyGroupId: Uuid, memoId: Uuid): Flow<Result<PagingData<SelectMemoTag>>> {
        return flow {
            getAccountUseCase().mapLatest { it.getOrThrow() }
                .flatMapLatest { account ->
                    when (account) {
                        is Account.Guest -> flowOf(Result.failure(NotLoginException()))
                        is Account.User -> buddyGroupSelectMemoTagRepository.page(account, buddyGroupId, memoId)
                            .mapLatest { Result.success(it) }
                    }
                }
                .also { emitAll(it) }
        }.catch {
            emit(Result.failure(it))
        }
    }
}
