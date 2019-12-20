package com.tushar.newsmvi.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NewsModel(
    @SerializedName("articles")
    var articles: List<Article>? = null,
    @SerializedName("status")
    var status: String = "",
    @SerializedName("totalResults")
    var totalResults: Int = 0
)