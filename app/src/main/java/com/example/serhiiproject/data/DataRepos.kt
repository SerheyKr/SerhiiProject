package com.example.serhiiproject.data

import com.example.serhiiproject.data.remote.Common
import com.example.serhiiproject.data.remote.model.PopularVideosData
import com.example.serhiiproject.data.remote.model.VideoData
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataRepos {

    private val api = Common.retrofitService
    private var dataSafe: MutableList<VideoData> = mutableListOf()
    public var gettingData = true

    suspend fun getData(): MutableList<VideoData>
    {
        val data: MutableList<VideoData> = mutableListOf()
        gettingData = true

        api.getPopularVideos().enqueue(object : Callback<PopularVideosData> {
            override fun onFailure(call: Call<PopularVideosData>, t: Throwable) {
                gettingData = false
            }

            override fun onResponse(
                call: Call<PopularVideosData>,
                response: Response<PopularVideosData>
            ) {
                val resp: PopularVideosData = response.body() as PopularVideosData

                resp.items?.forEach()
                {
                    data.add(
                        VideoData(
                        it.snippet!!.title,
                        "https://img.youtube.com/vi/${it.id}/0.jpg",
                        it.snippet,
                        it.id
                    )
                    )
                }
                if (data.isNotEmpty())
                    dataSafe.clear()
                dataSafe.addAll(data)
                gettingData = false
            }
        })

        while (gettingData)
        {
            delay(100)
        }

        return dataSafe
    }
}