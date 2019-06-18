package com.example.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_configuracoes.*
import kotlinx.android.synthetic.main.activity_menus.*

class ConfiguracoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        val myPreference = MyPreferences(this)
        var Username = myPreference.getUsername()
        etName.setText(Username)
    }

    public fun confirmarButton(view:View) {
        val username = etName.text.toString()
        val myPreference = MyPreferences(this)
        myPreference.setUsername(username)
        finish()
        val intent = Intent(this, MenusActivity::class.java)
        startActivity(intent)
    }
}
