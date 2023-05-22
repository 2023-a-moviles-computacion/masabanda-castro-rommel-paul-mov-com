import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    //Inmutable (No se reasignan)
    val inmutable: String ="Rommel";

    //Mutable (re asignar)
    var mutable: String = "Paul";
    mutable = "Rommel"

    //val > var

    //Duck typing
    val ejemploVariable = "Rommel Masabanda"
    val edadEjemplo: Int = 23
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo;

    //Variable primitiva
    val nombreProfesor: String = "Rommel Masabanda"
    val sueldo: Double = 1.2
    var estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clases Java
    val fechaNacimiento: Date = Date()

    //No existe el switch, pero si el when
    val estadoCivilWhen = "C"
    when(estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else ->{
            println("no sabemos")
        }
    }

    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.0)
    calcularSueldo(10.0, 20.0)
    calcularSueldo(10.0, 20.0, 30.0)
    calcularSueldo(10.0, bonoEspecial = 30.0)
    calcularSueldo(sueldo=10.0, tasa = 45.0, bonoEspecial = 18.0 )

}

fun imprimirNombre(nombre:String): Unit{
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //requerido
    tasa: Double = 12.0, //opcional(por defecto)
    bonoEspecial: Double? = null, //opcional (nullable)
): Double{
    if (bonoEspecial == null){
        return sueldo*(100/tasa)
    }else{
        return sueldo*(100/tasa)+bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class numeros(//constructor primario
    //ejemplo:
    //uno: Int (Parametro (sin modificar acceso))
    //private var uno : Int (Propiedad publica Clase numeros )
    //var uno:Int Propiedad de la clase por defecto publica
    protected val numeroUno:Int, //propiedad de la clase protected numeros.numeroUno
    protected val numeroDos:Int,
){
    init {
        this.numeroUno; this.numeroDos;//No es necesario el uso de this
        numeroUno; numeroDos;
        println("Inicializando")

    }
}
