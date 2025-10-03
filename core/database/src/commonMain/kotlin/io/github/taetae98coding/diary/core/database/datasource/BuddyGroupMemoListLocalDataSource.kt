package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupMemoListLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public fun page(buddyGroupId: Uuid): PagingSource<Int, MemoLocalEntity> {
        return database.buddyGroupMemoList().page(buddyGroupId)
    }
}
