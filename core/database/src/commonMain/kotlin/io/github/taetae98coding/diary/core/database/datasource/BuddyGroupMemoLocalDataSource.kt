package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupMemoLocalDataSource internal constructor(
    private val database: DiaryDatabase,
    private val transactor: DatabaseTransactor,
) {
    public suspend fun upsert(buddyGroupId: Uuid, entity: Collection<MemoLocalEntity>) {
        transactor.immediate {
            database.memo().upsert(entity)
            database.buddyGroupMemo().upsert(entity.map { BuddyGroupMemoLocalEntity(buddyGroupId, it.id) })
        }
    }

    public fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?> {
        return database.buddyGroupMemo().getLastUpdateAt(buddyGroupId)
    }
}
