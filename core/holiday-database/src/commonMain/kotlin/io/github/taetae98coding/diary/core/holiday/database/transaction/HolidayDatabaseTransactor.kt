package io.github.taetae98coding.diary.core.holiday.database.transaction

public interface HolidayDatabaseTransactor {
    public suspend fun immediate(
        action: suspend () -> Unit,
    )
}
