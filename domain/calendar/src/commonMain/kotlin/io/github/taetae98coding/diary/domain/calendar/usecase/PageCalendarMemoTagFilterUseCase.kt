package io.github.taetae98coding.diary.domain.calendar.usecase

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.MemoTagFilter
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.calendar.repository.CalendarMemoTagFilterTagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class PageCalendarMemoTagFilterUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val calendarMemoTagFilterTagRepository: CalendarMemoTagFilterTagRepository,
) {
    public operator fun invoke(): Flow<Result<PagingData<MemoTagFilter>>> {
        return flow {
            getAccountUseCase().mapLatest { it.getOrThrow() }
                .flatMapLatest { calendarMemoTagFilterTagRepository.page(it) }
                .also { emitAll(it) }
        }.mapLatest {
            Result.success(it)
        }.catch { emit(Result.failure(it)) }
    }
}
