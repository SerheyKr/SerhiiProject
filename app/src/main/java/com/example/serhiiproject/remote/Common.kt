package com.example.serhiiproject.remote

object Common {
    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}