package io.github.shadowrz.projectkafka.features.datamanage.impl

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import io.github.shadowrz.projectkafka.features.datamanage.impl.di.RestoreBindings
import io.github.shadowrz.projectkafka.libraries.architecture.bindings
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RestoreService : JobService() {
    private lateinit var bindings: RestoreBindings
    private var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        bindings = bindings()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        bindings.appCoroutineScope.cancel()
        job = bindings.appCoroutineScope.launch(
            context = bindings.coroutineDispatchers.io,
        ) {
        }

        return job?.isActive ?: true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        job?.cancel()
        return false
    }

    companion object {
        internal fun enqueueWork(context: Context) {
            val scheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val jobInfo = JobInfo
                .Builder(
                    0,
                    ComponentName(context, RestoreService::class.java),
                ).build()
            scheduler.schedule(jobInfo)
        }
    }
}
