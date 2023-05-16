package com.example.donatflix

import android.content.Context

class ResourcesProvider(private val context: Context) {

    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}