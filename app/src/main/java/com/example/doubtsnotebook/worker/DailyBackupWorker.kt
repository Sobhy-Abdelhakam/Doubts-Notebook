package com.example.doubtsnotebook.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.doubtsnotebook.data.backup.BackupManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyBackupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted parameters: WorkerParameters,
    private val backupManager: BackupManager
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        return try {
            backupManager.backupData()
            Result.success()
        } catch (e: Exception) {
            Log.e("Failure Worker", "Error in Worker $e")
            Result.retry()
        }
    }
}