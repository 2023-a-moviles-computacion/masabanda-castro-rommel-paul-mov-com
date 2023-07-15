import logica.Menu
import java.io.File

fun main() {

    val archivo = File("datos.txt")
    if (!archivo.exists()  ){
        archivo.createNewFile()
    }

    val menu = Menu()
    menu.menuInicial()

}


