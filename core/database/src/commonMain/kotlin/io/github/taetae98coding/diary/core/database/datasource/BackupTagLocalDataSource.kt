package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.BackupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class BackupTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<BackupTagLocalEntity>) {
        database.backupTag().upsert(entity)
    }

    public suspend fun delete(entity: Collection<BackupTagLocalEntity>) {
        database.backupTag().delete(entity)
    }

    public fun get(accountId: Uuid): Flow<List<TagLocalEntity>> {
        return database.backupTag().get(accountId)
    }
}
