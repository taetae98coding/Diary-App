package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.AccountTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class AccountTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<AccountTagLocalEntity>) {
        database.accountTag().upsert(entity)
    }

    public fun page(accountId: Uuid): PagingSource<Int, TagLocalEntity> {
        return database.accountTag().page(accountId)
    }
}
