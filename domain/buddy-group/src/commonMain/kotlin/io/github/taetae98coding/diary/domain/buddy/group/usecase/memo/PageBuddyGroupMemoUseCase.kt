package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoListRepository
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
public class PageBuddyGroupMemoUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupMemoListRepository: BuddyGroupMemoListRepository,
) {
    public operator fun invoke(buddyGroupId: Uuid): Flow<Result<PagingData<Memo>>> {
        return flow {
            getAccountUseCase().mapLatest { it.getOrThrow() }
                .flatMapLatest { account ->
                    when (account) {
                        is Account.Guest -> flowOf(Result.failure(NotLoginException()))
                        is Account.User -> buddyGroupMemoListRepository.page(account, buddyGroupId).mapLatest { Result.success(it) }
                    }
                }
                .also { emitAll(it) }
        }.catch {
            emit(Result.failure(it))
        }
    }
}
