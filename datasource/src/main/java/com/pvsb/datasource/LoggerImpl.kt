package com.pvsb.datasource

import android.util.Log
import com.pvsb.domain.util.Logger
import javax.inject.Inject

class LoggerImpl @Inject constructor() : Logger {

    override fun d(event: String) {
        Log.d("", event)
    }

    override fun e(exception: Exception) {
        Log.e("", "${exception.message}")
    }
}