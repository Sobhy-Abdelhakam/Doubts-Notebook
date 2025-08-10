package com.sobhy.debtnotebook.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object BackupScheduler {
    fun scheduleBackup(context: Context){
        val backupWorkRequest = PeriodicWorkRequestBuilder<DailyBackupWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_backup",
            ExistingPeriodicWorkPolicy.KEEP,
            backupWorkRequest
        )
    }

    private fun calculateInitialDelay(): Long {
        val now = LocalDateTime.now()
        val targetTime = now.withHour(2).withMinute(0).withSecond(0)

        val delay = Duration.between(now, targetTime)
            .takeIf { !it.isNegative }
            ?: Duration.between(now, targetTime.plusDays(1)) // If now after 2:00 AM

        return delay.toMillis()
    }
}