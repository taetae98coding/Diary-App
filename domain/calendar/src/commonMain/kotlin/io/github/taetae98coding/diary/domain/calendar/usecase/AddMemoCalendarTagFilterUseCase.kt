package io.github.taetae98coding.diary.domain.calendar.usecase

import io.github.taetae98coding.diary.domain.calendar.repository.MemoCalendarTagFilterRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class AddMemoCalendarTagFilterUseCase internal constructor(
    private val memoCalendarTagFilterRepository: MemoCalendarTagFilterRepository,
) {
    public suspend operator fun invoke(tagId: Uuid): Result<Unit> {
        return runCatching { memoCalendarTagFilterRepository.upsert(tagId, true) }
    }
}
