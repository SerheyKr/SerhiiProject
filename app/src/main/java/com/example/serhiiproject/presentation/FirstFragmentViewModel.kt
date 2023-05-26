package com.example.serhiiproject.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serhiiproject.data.remote.model.VideoData
import com.example.serhiiproject.domain.DataUseCase
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