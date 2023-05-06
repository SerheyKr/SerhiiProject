package com.example.serhiiproject.remote

import com.example.serhiiproject.dataClasses.PopularVideosData
import com.example.serhiiproject.dataClasses.VideoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataRepos {

    private val api = Common.retrofitService
    private var dataSafe: MutableList<VideoData> = mutableListOf()

    init {
        getData()
    }

    fun getData(): MutableList<VideoData>
    {
        var data: MutableList<VideoData> = mutableListOf()

        api.getPopularVideos().enqueue(object : Callback<PopularVideosData> {
            override fun onFailure(call: Call<PopularVideosData>, t: Throwable) {

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
                    ))
                }
                if (data.isNotEmpty())
                    dataSafe.clear()
                dataSafe.addAll(data)
            }
        })
        return dataSafe
    }
}