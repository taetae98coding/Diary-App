package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupMemoListTagFilterLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {

    public fun hasFilter(buddyGroupId: Uuid): Flow<Boolean> {
        return database.buddyGroupMemoListTagFilter().hasFilter(buddyGroupId)
    }

    public fun page(buddyGroupId: Uuid): PagingSource<Int, TagFilterLocalEntity> {
        return database.buddyGroupMemoListTagFilter().page(buddyGroupId)
    }
}
