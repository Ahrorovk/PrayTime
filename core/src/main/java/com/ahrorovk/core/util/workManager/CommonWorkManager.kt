package com.ahrorovk.core.util.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
class CommonWorkManager(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    fun worker(doWorker:()->Unit){
        doWorker()
    }
    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}