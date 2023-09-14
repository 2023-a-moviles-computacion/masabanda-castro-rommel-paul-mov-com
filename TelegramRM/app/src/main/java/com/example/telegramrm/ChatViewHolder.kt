package com.example.telegramrm

import android.transition.CircularPropagation
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

//se utiliza para representar una vista individual en el RecyclerView
class ChatViewHolder (view: View): RecyclerView.ViewHolder(view) {

    val fotoPerfil = view.findViewById<ImageView>(R.id.ivFotoPerfil)
    val nombre = view.findViewById<TextView>(R.id.tvNombre)
    val mensaje = view.findViewById<TextView>(R.id.tvMensaje)
    val hora = view.findViewById<TextView>(R.id.tvHora)
    val numMensajes = view.findViewById<TextView>(R.id.tvNumMensajes)
    val visto  = view.findViewById<ImageView>(R.id.tvVisto)

    //Muestra los datos de un objeto Chat en la vista.
    fun render(item: Chat) {
        Glide.with(fotoPerfil.context).load(item.fotoPerfil).transform(CircleCrop()).into(fotoPerfil)
        nombre.text = item.nombre
        mensaje.text = item.mensaje
        hora.text = item.hora

        if (item.numeroMensajes.toString()=="null"){
            numMensajes.text = ""
            numMensajes.setBackgroundResource(0)
        }else{
            numMensajes.text = item.numeroMensajes.toString()
            numMensajes.setBackgroundResource(R.drawable.por_ver)
        }

        //Cargar el recurso de imagen "visto"
        val resourceId = itemView.context.resources.getIdentifier(item.visto, "drawable", itemView.context.packageName)
        visto.setImageResource(resourceId)
    }


}