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
import io.github.taetae98coding.diary.core.work.worker.DiaryFetchAndroidxWorker
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
internal class DiarySyncWorkManagerImpl(
    private val context: Context,
) : DiarySyncWorkManager {
    override fun requestSync(account: Account.User) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val backupRequest = OneTimeWorkRequestBuilder<DiaryBackupAndroidxWorker>()
            .setInputData(DiaryBackupAndroidxWorker.data(account.id, account.token))
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()

        val fetchRequest = OneTimeWorkRequestBuilder<DiaryFetchAndroidxWorker>()
            .setInputData(DiaryFetchAndroidxWorker.data(account.token, account.id))
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .beginUniqueWork(
                uniqueWorkName = WorkManagerConstValue.syncWorkName(account.id),
                existingWorkPolicy = ExistingWorkPolicy.REPLACE,
                request = backupRequest,
            )
            .then(fetchRequest)
            .enqueue()
    }

    override suspend fun sync(account: Account.User) {
        requestSync(account)
        awaitBackupFinish(account)
    }

    private suspend fun awaitBackupFinish(account: Account.User) {
        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkFlow(WorkManagerConstValue.syncWorkName(account.id))
            .first { workInfoList -> workInfoList.all { it.state.isFinished } }
    }
}
