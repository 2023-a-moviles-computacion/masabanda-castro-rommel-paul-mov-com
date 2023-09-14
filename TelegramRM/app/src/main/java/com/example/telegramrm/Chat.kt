package com.example.telegramrm

data class Chat(
    val id: Int,
    val nombre : String,
    val mensaje : String,
    val fotoPerfil : String,
    val hora : String,
    val visto : String,
    val numeroMensajes : Int?
) {

}
