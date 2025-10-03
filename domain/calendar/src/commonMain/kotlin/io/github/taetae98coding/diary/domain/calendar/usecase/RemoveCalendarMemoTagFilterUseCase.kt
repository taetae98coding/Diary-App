package io.github.taetae98coding.diary.domain.calendar.usecase

import io.github.taetae98coding.diary.domain.calendar.repository.CalendarMemoTagFilterTagRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class RemoveCalendarMemoTagFilterUseCase internal constructor(
    private val calendarMemoTagFilterTagRepository: CalendarMemoTagFilterTagRepository,
) {
    public suspend operator fun invoke(tagId: Uuid): Result<Unit> {
        return runCatching { calendarMemoTagFilterTagRepository.upsert(tagId, false) }
    }
}
