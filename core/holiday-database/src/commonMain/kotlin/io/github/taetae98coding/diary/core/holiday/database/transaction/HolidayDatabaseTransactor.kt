package io.github.taetae98coding.diary.core.holiday.database.transaction

public interface HolidayDatabaseTransactor {
    public suspend fun transaction(
        action: suspend () -> Unit,
    )
}
