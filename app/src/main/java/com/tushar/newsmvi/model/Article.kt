package com.tushar.newsmvi.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Article(
    @SerializedName("author")
    var author: String = "",
    @SerializedName("content")
    var content: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("publishedAt")
    var publishedAt: String = "",
    @SerializedName("source")
    var source: Source = Source(),
    @SerializedName("title")
    var title: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("urlToImage")
    var urlToImage: String = ""
){
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Article

        if (publishedAt != other.publishedAt) return false

        return true
    }
}