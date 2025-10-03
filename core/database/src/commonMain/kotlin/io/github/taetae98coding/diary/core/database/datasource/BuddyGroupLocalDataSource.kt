package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<BuddyGroupLocalEntity>) {
        database.buddyGroup().upsert(entity)
    }

    public fun get(buddyGroupId: Uuid): Flow<BuddyGroupLocalEntity?> {
        return database.buddyGroup().get(buddyGroupId)
    }
}
