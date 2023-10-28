package com.udacity.asteroidradar.main

import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.ReposotryAstro
import com.udacity.asteroidradar.api.AstroiedAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



enum class FilterChoose{SAVED,TODAY, WEEK}

class AstroViewModelFactory(
    private val Repo: ReposotryAstro
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainViewModel(private val myRepositroy :  ReposotryAstro) : ViewModel() {



   //val savedAstroidList = myRepositroy.allAstroidList

    val menu = MutableLiveData<FilterChoose>(FilterChoose.SAVED)

    //picture of daayy
    private val _PictureOfDate = MutableLiveData<PictureOfDay>()
    val PictureOfDate: LiveData<PictureOfDay>
    get()= _PictureOfDate

    //object
    private val _navigateAstroidData = MutableLiveData<Asteroid>()
    val navigateAstroidData: LiveData<Asteroid>
    get() = _navigateAstroidData


    private val savedList = MutableLiveData<List<Asteroid>>()

    init {
      viewModelScope.launch {
                 myRepositroy.getAstroiedList()

                 val pic = AstroiedAPI.retrofitService.getImageOfDay()
                 _PictureOfDate.value = pic
             }
        }

    val UpdatedData :LiveData<List<Asteroid>> = Transformations.switchMap(menu){ new ->
        when(new){
            FilterChoose.TODAY -> myRepositroy.today()
            FilterChoose.WEEK -> myRepositroy.week()
            FilterChoose.SAVED->myRepositroy.saved()
        }
    }

    fun pass(astroid: Asteroid)
    {
        _navigateAstroidData.value = astroid
    }

    fun clearPass()
    {
        _navigateAstroidData.value = null
    }


    fun saved()
    {
        menu.value=FilterChoose.SAVED
    }
    fun today()
    {
        menu.value=FilterChoose.TODAY
    }
    fun week()
    {
        menu.value=FilterChoose.WEEK
    }

}
