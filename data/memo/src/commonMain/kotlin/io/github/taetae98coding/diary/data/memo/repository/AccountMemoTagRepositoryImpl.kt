package io.github.taetae98coding.diary.data.memo.repository

import io.github.taetae98coding.diary.core.database.datasource.BackupMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BackupMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoTagRepository
import kotlin.time.Clock
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
internal class AccountMemoTagRepositoryImpl(
    private val clock: Clock,
    private val transactor: DatabaseTransactor,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val backupMemoTagLocalDataSource: BackupMemoTagLocalDataSource,
) : AccountMemoTagRepository {
    override suspend fun updateMemoTag(
        account: Account,
        memoId: Uuid,
        tagId: Uuid,
        isMemoTag: Boolean,
    ) {
        val local = MemoTagLocalEntity(
            memoId = memoId,
            tagId = tagId,
            isMemoTag = isMemoTag,
            updatedAt = clock.now().toEpochMilliseconds(),
            serverUpdatedAt = -1L,
        )

        transactor.immediate {
            memoTagLocalDataSource.upsert(listOf(local))
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupMemoTagLocalDataSource.upsert(listOf(BackupMemoTagLocalEntity(memoId, tagId)))
            }
        }
    }
}
