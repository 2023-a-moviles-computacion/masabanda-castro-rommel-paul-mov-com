package com.example.telegramrm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MensajeAdapter (
    private val mensajeList: List<Mensaje>

    ): RecyclerView.Adapter<MensajeViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MensajeViewHolder(layoutInflater.inflate(R.layout.item_mensaje, parent,false))
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val item = mensajeList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return mensajeList.size
    }


}