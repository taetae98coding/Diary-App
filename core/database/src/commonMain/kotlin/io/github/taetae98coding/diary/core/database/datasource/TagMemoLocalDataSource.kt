package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class TagMemoLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public fun page(tagId: Uuid): PagingSource<Int, MemoLocalEntity> {
        return database.tagMemo().page(tagId)
    }
}
