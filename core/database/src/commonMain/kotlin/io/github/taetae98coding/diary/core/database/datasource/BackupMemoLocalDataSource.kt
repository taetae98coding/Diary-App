package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.BackupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class BackupMemoLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<BackupMemoLocalEntity>) {
        database.backupMemo().upsert(entity)
    }

    public suspend fun delete(entity: Collection<BackupMemoLocalEntity>) {
        database.backupMemo().delete(entity)
    }

    public fun get(accountId: Uuid): Flow<List<MemoLocalEntity>> {
        return database.backupMemo().get(accountId)
    }
}
