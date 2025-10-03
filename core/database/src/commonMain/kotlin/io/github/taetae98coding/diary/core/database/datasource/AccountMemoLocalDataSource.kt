package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.AccountMemoLocalEntity
import org.koin.core.annotation.Factory

@Factory
public class AccountMemoLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: List<AccountMemoLocalEntity>) {
        database.accountMemo().upsert(entity)
    }
}
