package com.example.serhiiproject.remote

import com.example.serhiiproject.dataClasses.PopularVideosData
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("videos?part=id%2C+snippet&chart=mostPopular&regionCode=FR&key=AIzaSyD7HSW-5M6HejEc_Ab7v5Sxfpc2cqqldsY&maxResults=20")
    fun getPopularVideos(): Call<PopularVideosData>
    @GET("videos?part=id%2C+snippet&key=AIzaSyD7HSW-5M6HejEc_Ab7v5Sxfpc2cqqldsY")
    fun getVideoInfo(@Query("id") id: String?): Call<PopularVideosData>
}