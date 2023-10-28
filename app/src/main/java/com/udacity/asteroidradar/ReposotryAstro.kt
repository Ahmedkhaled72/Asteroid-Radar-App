package com.udacity.asteroidradar

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Room.DataBaseAstriod
import com.udacity.asteroidradar.api.AstroiedAPI
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class ReposotryAstro(private val database: DataBaseAstriod) {

    val formatdata = SimpleDateFormat("yyyy-MM-dd")
    val clender = Calendar.getInstance()
    val dateForNow = formatdata.format(clender.time)

    //get all list of astroid From Data base
   // val allAstroidList = database.databaseDao.getAllAstriod()

    fun saved():LiveData<List<Asteroid>> = database.databaseDao.getAllAstriod()
    fun today(): LiveData<List<Asteroid>> = database.databaseDao.getTodayAstro(dateForNow)
    fun week():LiveData<List<Asteroid>> = database.databaseDao.getWeekAstro(dateForNow)


    suspend fun getAstroiedList() {
        withContext(Dispatchers.IO){
            try {
                    val Data =  AstroiedAPI.retrofitService.getitems(format()[0],format()[1])
                    database.databaseDao.insertAstriod(parseAsteroidsJsonResult(JSONObject(Data.string())))

                } catch (e:Exception){

            }

    }
}

    private fun format():List<String>
    {
        val formatdata = SimpleDateFormat("yyyy-MM-dd")
        val clender = Calendar.getInstance()
        val dateForNow = formatdata.format(clender.time)
        clender.add(Calendar.DATE,7)
        val dataForAfter = formatdata.format(clender.time)
        return listOf(dateForNow,dataForAfter)
    }
}