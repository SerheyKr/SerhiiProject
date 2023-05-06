package com.example.serhiiproject.remote

import com.example.serhiiproject.dataClasses.VideoData
import kotlinx.coroutines.delay

object DataUseCase {
    private val rep = DataRepos
    suspend fun getData(): MutableList<VideoData>
    {
        val data = rep.getData();
        delay(200)
        return data
    }
}