package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.BackupMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class BackupMemoTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<BackupMemoTagLocalEntity>) {
        database.backupMemoTag().upsert(entity)
    }

    public fun get(accountId: Uuid): Flow<List<MemoTagLocalEntity>> {
        return database.backupMemoTag().get(accountId)
    }

    public suspend fun delete(entity: Collection<BackupMemoTagLocalEntity>) {
        database.backupMemoTag().delete(entity)
    }
}
