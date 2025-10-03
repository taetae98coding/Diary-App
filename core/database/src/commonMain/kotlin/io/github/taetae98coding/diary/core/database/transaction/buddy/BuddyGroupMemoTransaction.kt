package io.github.taetae98coding.diary.core.database.transaction.buddy

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupMemoTransaction internal constructor(
    private val transactor: DatabaseTransactor,
    private val memoLocalDataSource: MemoLocalDataSource,
    private val buddyGroupMemoLocalDataSource: BuddyGroupMemoLocalDataSource,
) {
    public suspend fun upsert(buddyGroupId: Uuid, entity: List<MemoLocalEntity>) {
        transactor.transaction {
            memoLocalDataSource.upsert(entity)
            buddyGroupMemoLocalDataSource.upsert(entity.map { BuddyGroupMemoLocalEntity(buddyGroupId, it.id) })
        }
    }
}
