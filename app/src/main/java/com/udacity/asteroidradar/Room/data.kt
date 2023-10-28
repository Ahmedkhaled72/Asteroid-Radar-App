package com.udacity.asteroidradar.Room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface DataBaseDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAstriod(astriod: ArrayList<Asteroid>)

    @Query("SELECT * FROM AsteroidTable ORDER BY closeApproachDate")
    fun getAllAstriod(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM AsteroidTable WHERE closeApproachDate= :today")
    fun getTodayAstro(today:String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM AsteroidTable WHERE closeApproachDate >= :today ORDER BY closeApproachDate")
    fun getWeekAstro(today:String): LiveData<List<Asteroid>>

    @Query("DELETE FROM AsteroidTable")
    suspend fun clear()
}

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class DataBaseAstriod: RoomDatabase()
{
    abstract val databaseDao: DataBaseDao

    companion object{

        private var Instance: DataBaseAstriod? = null
        fun getDataBase(context: Context):DataBaseAstriod{
            var instance = Instance

            if(instance == null)
            {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseAstriod::class.java,
                    "AstroidRadar").fallbackToDestructiveMigration().build()
                Instance = instance
            }

            return instance
        }
    }


}