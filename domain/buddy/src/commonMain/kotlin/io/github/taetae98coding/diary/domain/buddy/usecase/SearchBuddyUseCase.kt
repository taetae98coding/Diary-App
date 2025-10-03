package io.github.taetae98coding.diary.domain.buddy.usecase

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.repository.BuddyRepository
import io.github.taetae98coding.diary.library.paging.common.notLoading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class SearchBuddyUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyRepository: BuddyRepository,
) {
    public operator fun invoke(query: String): Flow<Result<PagingData<Buddy>>> {
        return flow {
            if (query.isBlank()) {
                emit(PagingData.notLoading())
                return@flow
            }

            getAccountUseCase().mapLatest { it.getOrThrow() }
                .flatMapLatest { buddyRepository.search(it, query) }
                .also { emitAll(it) }
        }.mapLatest {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }
    }
}
