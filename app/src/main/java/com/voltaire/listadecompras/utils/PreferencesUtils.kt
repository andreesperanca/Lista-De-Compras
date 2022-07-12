package com.voltaire.listadecompras.utils

import android.content.Context

class PreferencesUtils (context: Context) {

    val sPrefs = context.getSharedPreferences(KEY_WATCH_ONBOARDING, Context.MODE_PRIVATE)

    companion object {
        const val KEY_WATCH_ONBOARDING = "com.voltaire.listadecompras.onboarding"
        const val DEFAULT_VALUE = false
    }

    fun wasSeen() : Boolean {
        return sPrefs.getBoolean(KEY_WATCH_ONBOARDING, DEFAULT_VALUE)
    }

    fun saveVisualization() {
        sPrefs
            .edit()
            .putBoolean(KEY_WATCH_ONBOARDING, true)
            .commit()
    }
}