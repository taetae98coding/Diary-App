package io.github.taetae98coding.diary.core.work.worker.backup

import io.github.taetae98coding.diary.core.database.datasource.BackupMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BackupMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.MemoTagRemoteDataSource
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class MemoTagBackupWorker(
    private val backupMemoTagLocalDataSource: BackupMemoTagLocalDataSource,
    private val memoTagRemoteDataSource: MemoTagRemoteDataSource,
) : EntityBackupWorker<MemoTagLocalEntity>() {
    override suspend fun getBackupList(accountId: Uuid): List<MemoTagLocalEntity> {
        return backupMemoTagLocalDataSource.get(accountId).first()
    }

    override suspend fun backup(token: String, list: List<MemoTagLocalEntity>) {
        memoTagRemoteDataSource.upsert(token, list.map(MemoTagLocalEntity::toRemote))
    }

    override suspend fun removeBackupList(accountId: Uuid, list: List<MemoTagLocalEntity>) {
        backupMemoTagLocalDataSource.delete(list.map { BackupMemoTagLocalEntity(it.memoId, it.tagId) })
    }
}
