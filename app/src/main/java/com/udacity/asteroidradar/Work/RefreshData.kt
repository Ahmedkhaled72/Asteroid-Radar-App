package com.udacity.asteroidradar.Work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.udacity.asteroidradar.ReposotryAstro
import com.udacity.asteroidradar.Room.DataBaseAstriod.Companion.getDataBase
import retrofit2.HttpException

class RefreshData(appContext: Context, params: WorkerParameters):
CoroutineWorker(appContext,params)
{
    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database = getDataBase(applicationContext)
        val repository = ReposotryAstro(database)



        return try {
            repository.getAstroiedList()
            Result.success()
        }catch(e: HttpException){
            Result.retry()
        }
    }

}