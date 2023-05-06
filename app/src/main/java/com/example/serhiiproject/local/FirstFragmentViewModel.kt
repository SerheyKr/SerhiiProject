package com.example.serhiiproject.local

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serhiiproject.dataClasses.VideoData
import com.example.serhiiproject.remote.DataUseCase
import kotlinx.coroutines.launch

class FirstFragmentViewModel(): ViewModel()
{
    var videosData = MutableLiveData<MutableList<VideoData>>()
    var isLoading = MutableLiveData<Boolean>()

    private val dataUseCase: DataUseCase = DataUseCase;

    fun updateInformation()
    {
        this.viewModelScope.launch()
        {
            isLoading.postValue(true)
            videosData.postValue(dataUseCase.getData())
            isLoading.postValue(false)
        }
    }
}