package io.github.taetae98coding.diary.core.holiday.database.transaction

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import io.github.taetae98coding.diary.core.holiday.database.HolidayDatabase
import org.koin.core.annotation.Factory

@Factory
internal class HolidayDatabaseTransactorImpl(
    private val database: HolidayDatabase,
) : HolidayDatabaseTransactor {
    override suspend fun immediate(
        action: suspend () -> Unit,
    ) {
        database.useWriterConnection { transactor ->
            transactor.immediateTransaction {
                action()
            }
        }
    }
}
