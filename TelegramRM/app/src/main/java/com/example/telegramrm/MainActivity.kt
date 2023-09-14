package com.example.telegramrm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Acceder a la lista de chats
        ChatProvider.chatList
        setContentView(R.layout.activity_main)
        // Inicializar el RecyclerView
        initRecyclerView()
    }
    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerChat)
        //para organizar los elementos en la lista verticalmente
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crear un adaptador (ChatAdapter) para el RecyclerView utilizando la lista de chats.
        val chatAdapter = ChatAdapter(ChatProvider.chatList){
            chat ->
            // Aquí se maneja el clic en un elemento del RecyclerView
            val intent = Intent(this, MensajeActivity::class.java)
            intent.putExtra("chat_id", chat.id)
            startActivity(intent)
        }
        // Establecer el adaptador en el RecyclerView.
        recyclerView.adapter = chatAdapter

        // Crear un DividerItemDecoration con un contexto y una orientación
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        // Establecer el Drawable del separador personalizado
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.item_divider)!!)

        recyclerView.addItemDecoration(dividerItemDecoration)

    }
}