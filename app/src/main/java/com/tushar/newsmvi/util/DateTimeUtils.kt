package com.tushar.newsmvi.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    fun getFormattedDate(stringDate: String) : Date{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        var date: Date? = null
        try {
            date = dateFormat.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date ?: Date()
    }
}