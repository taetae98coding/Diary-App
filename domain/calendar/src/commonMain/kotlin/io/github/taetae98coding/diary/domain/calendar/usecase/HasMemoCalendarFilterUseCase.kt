package io.github.taetae98coding.diary.domain.calendar.usecase

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
public class HasMemoCalendarFilterUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val memoCalendarTagFilterTagRepository: MemoCalendarTagFilterRepository,
) {
    public operator fun invoke(): Flow<Result<Boolean>> {
        return flow {
            getAccountUseCase().mapLatest { it.getOrThrow() }
                .flatMapLatest { memoCalendarTagFilterTagRepository.hasFilter(it) }
                .also { emitAll(it) }
        }.mapLatest {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }
    }
}
