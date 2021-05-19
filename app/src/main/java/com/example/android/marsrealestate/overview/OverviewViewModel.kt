package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    private fun getMarsRealEstateProperties(filter: MarsApiFilter){
        coroutineScope.launch {
            var getPropertyDeferred =  MarsApi.retrofitService.getProperties(filter.value)
            try{
                _status.value = MarsApiStatus.LOADING
                val listResult = getPropertyDeferred.await()
                _status.value = MarsApiStatus.DONE
                _properties.value = listResult
            }catch (e:Exception){
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

enum class MarsApiStatus{ LOADING, ERROR, DONE }