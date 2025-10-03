package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupMemoTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?> {
        return database.buddyGroupMemoTag().getLastUpdateAt(buddyGroupId)
    }
}
