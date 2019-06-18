package com.example.myapplication

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_coisa.*

class CoisaActivity : AppCompatActivity() {

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coisa)

        try {
            var bundle: Bundle = intent.extras
            id = bundle.getInt("MainActId", 0)
            if (id != 0) {
                edtNome.setText(bundle.getString("MainActTitle"))
                edtDescricao.setText(bundle.getString("MainActContent"))
            }
        } catch (ex: Exception) {
        }

        btAdd.setOnClickListener {
            var dbManager = CoisasDbManager(this)

            var values = ContentValues()
            values.put("nome", edtNome.text.toString())
            values.put("descricao", edtDescricao.text.toString())

            if (id == 0) {
                val mID = dbManager.insert(values)

                if (mID > 0) {
                    Toast.makeText(this, "Add note successfully!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Fail to add note!", Toast.LENGTH_LONG).show()
                }
            } else {
                var selectionArs = arrayOf(id.toString())
                val mID = dbManager.update(values, "Id=?", selectionArs)

                if (mID > 0) {
                    Toast.makeText(this, "Add note successfully!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Fail to add note!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}