package com.example.telegramrm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class MensajeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MensajeProvider.mensajeList
        setContentView(R.layout.activity_mensaje)
        initRecyclerView()

        // Recuperar el valor pasado en el Intent
        val chatId = intent.getIntExtra("chat_id", -1)

        val chatSeleccionado: Chat? = ChatProvider.getChatById(chatId)

        val nombre = findViewById<TextView>(R.id.tv_nombreContactoMensaje)
        val foto = findViewById<ImageView>(R.id.ivFotoPerfilMensaje)
        if (chatSeleccionado != null) {
            nombre.setText(chatSeleccionado.nombre)
            Glide.with(foto.context).load(chatSeleccionado.fotoPerfil).transform(CircleCrop()).into(foto)
        }


    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMensajes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MensajeAdapter(MensajeProvider.mensajeList)

    }
}