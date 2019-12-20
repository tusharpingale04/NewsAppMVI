package com.tushar.newsmvi.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Source(
    @SerializedName("id")
    var id: Any = Any(),
    @SerializedName("name")
    var name: String = ""
)