package com.example.telegramrm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//Proporcionar los datos y gestionar cómo se muestran los chats
class ChatAdapter(
    //datos a mostrar en el rv
    private  val chatList: List<Chat>,
    //lamda toma un chat cuando se haga click
    private val onItemClick: (Chat) -> Unit

): RecyclerView.Adapter<ChatViewHolder>() {

    //crear la estructura de un nuevo elemento para el rv
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChatViewHolder(layoutInflater.inflate(R.layout.item_chat, parent,false))
    }

    //devuelve la cantidad de elementos en la lista de chats
    //le dice al rv cuantos elementos mostrar
    override fun getItemCount(): Int {
        return chatList.size
    }

    //enlaza un elemento de la lista de chat con ChatViewHolder
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = chatList[position]
        //mostrar los datos en la vista correspondiente
        holder.render(item)
        //detectar clic sobre un elemento del rv
        holder.itemView.setOnClickListener{
            onItemClick(item)
        }
    }

}