package io.github.taetae98coding.diary.core.work.manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.work.WorkManagerConstValue
import io.github.taetae98coding.diary.core.work.worker.DiaryBackupAndroidxWorker
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class DiaryBackupWorkManagerImpl(
    private val context: Context,
) : DiaryBackupWorkManager {
    override fun requestBackup(account: Account.User) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<DiaryBackupAndroidxWorker>()
            .setInputData(DiaryBackupAndroidxWorker.data(account.id, account.token))
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                uniqueWorkName = WorkManagerConstValue.syncWorkName(account.id),
                existingWorkPolicy = ExistingWorkPolicy.APPEND_OR_REPLACE,
                request = request,
            )
    }

    override suspend fun backup(account: Account.User) {
        requestBackup(account)
        awaitBackupFinish(account)
    }

    private suspend fun awaitBackupFinish(account: Account.User) {
        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkFlow(WorkManagerConstValue.syncWorkName(account.id))
            .first { workInfoList -> workInfoList.all { it.state.isFinished } }
    }
}
