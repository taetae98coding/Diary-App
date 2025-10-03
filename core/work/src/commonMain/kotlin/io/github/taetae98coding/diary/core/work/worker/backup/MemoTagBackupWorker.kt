package io.github.taetae98coding.diary.core.work.worker.backup

import io.github.taetae98coding.diary.core.database.datasource.BackupMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BackupMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.MemoTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class MemoTagBackupWorker(
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val backupMemoTagLocalDataSource: BackupMemoTagLocalDataSource,
    private val memoTagRemoteDataSource: MemoTagRemoteDataSource,
) : EntityBackupWorker<MemoTagLocalEntity>() {
    override suspend fun getBackupList(accountId: Uuid): List<MemoTagLocalEntity> {
        return backupMemoTagLocalDataSource.get(accountId).first()
    }

    override suspend fun backup(accountId: Uuid, token: String, list: List<MemoTagLocalEntity>) {
        val remote = memoTagRemoteDataSource.upsert(token, list.map(MemoTagLocalEntity::toRemote))
            .requireSuccess()
            .requireBody()

        memoTagLocalDataSource.upsert(remote.map(MemoTagRemoteEntity::toLocal))
    }

    override suspend fun removeBackupList(accountId: Uuid, list: List<MemoTagLocalEntity>) {
        backupMemoTagLocalDataSource.delete(list.map { BackupMemoTagLocalEntity(it.memoId, it.tagId) })
    }
}
