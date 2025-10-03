package io.github.taetae98coding.diary.data.memo.repository

import io.github.taetae98coding.diary.core.database.datasource.AccountMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BackupMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BackupMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BackupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BackupMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import kotlin.time.Clock
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
internal class AccountMemoRepositoryImpl(
    private val clock: Clock,
    private val transactor: DatabaseTransactor,
    private val accountMemoLocalDataSource: AccountMemoLocalDataSource,
    private val memoLocalDataSource: MemoLocalDataSource,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val backupMemoLocalDataSource: BackupMemoLocalDataSource,
    private val backupMemoTagLocalDataSource: BackupMemoTagLocalDataSource,
) : AccountMemoRepository {

    override suspend fun add(account: Account, memo: Memo, memoTagIds: Set<Uuid>) {
        transactor.immediate {
            accountMemoLocalDataSource.upsert(account.id, listOf(memo.toLocal()))
            memoTagLocalDataSource.upsert(memoTagIds.map { MemoTagLocalEntity(memo.id, it, true, clock.now().toEpochMilliseconds(), -1) })
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> {
                    backupMemoLocalDataSource.upsert(listOf(BackupMemoLocalEntity(account.id, memo.id)))
                    backupMemoTagLocalDataSource.upsert(memoTagIds.map { BackupMemoTagLocalEntity(memo.id, it) })
                }
            }
        }
    }

    override suspend fun update(account: Account, memoId: Uuid, detail: MemoDetail) {
        transactor.immediate {
            memoLocalDataSource.update(memoId, detail.toLocal())
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupMemoLocalDataSource.upsert(listOf(BackupMemoLocalEntity(account.id, memoId)))
            }
        }
    }

    override suspend fun updateFinished(account: Account, memoId: Uuid, isFinished: Boolean) {
        transactor.immediate {
            memoLocalDataSource.updateFinished(memoId, isFinished)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupMemoLocalDataSource.upsert(listOf(BackupMemoLocalEntity(account.id, memoId)))
            }
        }
    }

    override suspend fun updateDeleted(account: Account, memoId: Uuid, isDeleted: Boolean) {
        transactor.immediate {
            memoLocalDataSource.updateDeleted(memoId, isDeleted)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupMemoLocalDataSource.upsert(listOf(BackupMemoLocalEntity(account.id, memoId)))
            }
        }
    }

    override suspend fun updatePrimaryTag(account: Account, memoId: Uuid, tagId: Uuid?) {
        transactor.immediate {
            memoLocalDataSource.updatePrimaryTag(memoId, tagId)
            when (account) {
                is Account.Guest -> Unit
                is Account.User -> backupMemoLocalDataSource.upsert(listOf(BackupMemoLocalEntity(account.id, memoId)))
            }
        }
    }
}
