package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//MOSHI TO CONVERT DATA FROM JASON TO USEFUL OBJECT
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


// retrofit that btt3amel m3
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl("https://api.nasa.gov/")
    .build()

interface AstroidApiService{
     //here we request from web service

    @GET("neo/rest/v1/feed")
    suspend fun getitems(
        @Query("start_date")strat:String
        ,@Query("end_date")end:String,
        @Query("api_key") key:String="xVzj8gmXXiDD3eBtcUdsBvjExOS9MOV9gY9b6gTs"):
            ResponseBody  //or object

   @GET("planetary/apod")
   suspend fun getImageOfDay(@Query("api_key") key:String="xVzj8gmXXiDD3eBtcUdsBvjExOS9MOV9gY9b6gTs"):PictureOfDay

}

object AstroiedAPI{

     val retrofitService: AstroidApiService by lazy {
         retrofit.create(AstroidApiService::class.java)
     }
}



