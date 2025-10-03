package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
    private val transactor: DatabaseTransactor,
) {
    public suspend fun upsert(buddyGroupId: Uuid, entity: Collection<TagLocalEntity>) {
        transactor.immediate {
            database.tag().upsert(entity)
            database.buddyGroupTag().upsert(entity.map { BuddyGroupTagLocalEntity(buddyGroupId, it.id) })
        }
    }

    public fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?> {
        return database.buddyGroupTag().getLastUpdateAt(buddyGroupId)
    }

    public fun page(buddyGroupId: Uuid): PagingSource<Int, TagLocalEntity> {
        return database.buddyGroupTag().page(buddyGroupId)
    }
}
