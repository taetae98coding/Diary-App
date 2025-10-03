package io.github.taetae98coding.diary.core.work.worker.backup

import io.github.taetae98coding.diary.core.database.datasource.AccountMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BackupMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BackupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.MemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class MemoBackupWorker(
    private val accountMemoLocalDataSource: AccountMemoLocalDataSource,
    private val backupMemoLocalDataSource: BackupMemoLocalDataSource,
    private val memoRemoteDataSource: MemoRemoteDataSource,
) : EntityBackupWorker<MemoLocalEntity>() {
    override suspend fun getBackupList(accountId: Uuid): List<MemoLocalEntity> {
        return backupMemoLocalDataSource.get(accountId).first()
    }

    override suspend fun backup(accountId: Uuid, token: String, list: List<MemoLocalEntity>) {
        val remote = memoRemoteDataSource.upsert(token, list.map(MemoLocalEntity::toRemote))
            .requireSuccess()
            .requireBody()

        accountMemoLocalDataSource.upsert(accountId, remote.map(MemoRemoteEntity::toLocal))
    }

    override suspend fun removeBackupList(accountId: Uuid, list: List<MemoLocalEntity>) {
        backupMemoLocalDataSource.delete(list.map { BackupMemoLocalEntity(accountId, it.id) })
    }
}
