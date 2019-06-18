package com.example.myapplication

import android.content.Context

class MyPreferences(context: Context) {
    val PREFERENCE_NAME = "SharedPreferencesExample"
    val PREFERENCE_USERNAME = "Username"
    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getUsername() : String {
        return preference.getString(PREFERENCE_USERNAME, "Nome do usuário")
    }

    fun setUsername(username:String) {
        val editor = preference.edit()
        editor.putString(PREFERENCE_USERNAME,username)
        editor.apply()
    }
}