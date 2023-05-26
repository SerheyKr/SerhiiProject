package com.example.serhiiproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class PopularVideosData
    (
    @SerializedName("kind")
    var kind: String? = null,
    @SerializedName("etag")
    var etag: String? = null,
    @SerializedName("items")
    var items: List<VideoData>? = null
)