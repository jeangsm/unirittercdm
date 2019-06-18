package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_menus.*

class MenusActivity : AppCompatActivity() {

    private var listCoisas = ArrayList<Coisa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menus)
        val myPreference = MyPreferences(this)
        val Username = myPreference.getUsername()
        textView2.text = Username
        Log.d("testando", "Passou das alterações iniciais!")
        loadQueryAll()
        Log.d("testando", "Passou da loadQueryAll!")
        lvCoisas.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, "Click on " + listCoisas[position].nome, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.addCoisa -> {
                    var intent = Intent(this, CoisaActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        loadQueryAll()
    }

    fun loadQueryAll() {

        var dbManager = CoisasDbManager(this)
        val cursor = dbManager.queryAll()

        listCoisas.clear()
        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("nome"))
                val content = cursor.getString(cursor.getColumnIndex("descricao"))

                listCoisas.add(Coisa(id, title, content))

            } while (cursor.moveToNext())
        }

        var notesAdapter = NotesAdapter(this, listCoisas)
        lvCoisas.adapter = notesAdapter
    }

    inner class NotesAdapter : BaseAdapter {

        private var coisasList = ArrayList<Coisa>()
        private var context: Context? = null

        constructor(context: Context, notesList: ArrayList<Coisa>) : super() {
            this.coisasList = notesList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.coisa, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            var mCoisa = coisasList[position]

            vh.tvNome.text = mCoisa.nome
            vh.tvDescricao.text = mCoisa.descricao

            vh.ivEdit.setOnClickListener {
                updateCoisa(mCoisa)
            }

            vh.ivDelete.setOnClickListener {
                var dbManager = CoisasDbManager(this.context!!)
                val selectionArgs = arrayOf(mCoisa.id.toString())
                dbManager.delete("Id=?", selectionArgs)
                loadQueryAll()
            }

            return view
        }

        override fun getItem(position: Int): Any {
            return coisasList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return coisasList.size
        }
    }

    private fun updateCoisa(coisa: Coisa) {
        var intent = Intent(this, CoisaActivity::class.java)
        intent.putExtra("MainActId", coisa.id)
        intent.putExtra("MainActTitle", coisa.nome)
        intent.putExtra("MainActContent", coisa.descricao)
        startActivity(intent)
    }

    private class ViewHolder(view: View?) {
        val tvNome: TextView
        val tvDescricao: TextView
        val ivEdit: ImageView
        val ivDelete: ImageView

//        init {
//            this.tvNome = view?.findViewById(R.id.tvNome) as TextView
//            this.tvDescricao = view?.findViewById(R.id.tvDescricao) as TextView
//            this.ivEdit = view?.findViewById(R.id.ivEdit) as ImageView
//            this.ivDelete = view?.findViewById(R.id.ivDelete) as ImageView
//        }

        // with API 26
        init {
            this.tvNome = view?.findViewById<TextView>(R.id.tvNome) as TextView
            this.tvDescricao = view?.findViewById<TextView>(R.id.tvDescricao) as TextView
            this.ivEdit = view?.findViewById<ImageView>(R.id.ivEdit) as ImageView
            this.ivDelete = view?.findViewById<ImageView>(R.id.ivDelete) as ImageView
        }
    }

    public fun configButton (view: View) {
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
        finish()
    }
}