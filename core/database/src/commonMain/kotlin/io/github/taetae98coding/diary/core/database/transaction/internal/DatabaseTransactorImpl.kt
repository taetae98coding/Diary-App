package io.github.taetae98coding.diary.core.database.transaction.internal

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import org.koin.core.annotation.Factory

@Factory
internal class DatabaseTransactorImpl(
    private val database: DiaryDatabase,
) : DatabaseTransactor {
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
