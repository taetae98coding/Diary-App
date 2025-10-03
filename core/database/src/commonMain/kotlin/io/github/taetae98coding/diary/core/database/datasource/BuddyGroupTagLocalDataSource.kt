package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<BuddyGroupTagLocalEntity>) {
        database.buddyGroupTag().upsert(entity)
    }

    public suspend fun delete(buddyGroupId: Uuid) {
        database.buddyGroupTag().delete(buddyGroupId)
    }

    public fun page(buddyGroupId: Uuid): PagingSource<Int, TagLocalEntity> {
        return database.buddyGroupTag().page(buddyGroupId)
    }
}
