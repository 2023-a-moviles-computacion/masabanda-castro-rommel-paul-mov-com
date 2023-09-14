package com.example.telegramrm

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MensajeViewHolder (view: View): RecyclerView.ViewHolder(view) {

    val contenido = view.findViewById<TextView>(R.id.tv_contenido)
    var hora = view.findViewById<TextView>(R.id.tv_hora_mensaje)

    fun render(item: Mensaje){
        contenido.text = item.contenido
        hora.text = item.hora
    }

}
