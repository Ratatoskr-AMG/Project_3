package net.doheco.domain.utils

import android.content.Context

class GetResource(val context: Context) {
    fun getString(id: Int): String{
        return context.getString(id)
    }
}