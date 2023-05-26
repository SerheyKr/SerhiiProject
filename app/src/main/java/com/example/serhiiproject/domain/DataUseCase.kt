package com.example.serhiiproject.domain

import com.example.serhiiproject.data.DataRepos
import com.example.serhiiproject.data.remote.model.VideoData

object DataUseCase {
    private val rep = DataRepos
    suspend fun getData(): MutableList<VideoData>
    {
        val data = DataRepos.getData();
        return data
    }
}