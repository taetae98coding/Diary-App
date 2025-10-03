package io.github.taetae98coding.diary.domain.calendar.usecase

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.calendar.repository.MemoCalendarTagFilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class PageMemoCalendarTagFilterUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val memoCalendarTagFilterRepository: MemoCalendarTagFilterRepository,
) {
    public operator fun invoke(): Flow<Result<PagingData<TagFilter>>> {
        return flow {
            getAccountUseCase().mapLatest { it.getOrThrow() }
                .flatMapLatest { memoCalendarTagFilterRepository.page(it) }
                .also { emitAll(it) }
        }.mapLatest {
            Result.success(it)
        }.catch { emit(Result.failure(it)) }
    }
}
