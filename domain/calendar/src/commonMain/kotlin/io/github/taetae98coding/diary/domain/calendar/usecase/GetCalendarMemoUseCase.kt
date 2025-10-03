package io.github.taetae98coding.diary.domain.calendar.usecase

import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.calendar.repository.CalendarMemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
public class GetCalendarMemoUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val calendarMemoRepository: CalendarMemoRepository,
) {
    public operator fun invoke(dateRange: LocalDateRange): Flow<Result<List<CalendarMemo>>> {
        return flow {
            getAccountUseCase().mapLatest { it.getOrThrow() }
                .flatMapLatest { calendarMemoRepository.get(it, dateRange) }
                .also { emitAll(it) }
        }.mapLatest {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }
    }
}
