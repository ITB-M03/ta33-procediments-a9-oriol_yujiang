package controllers.Main

import utilities.abrirScanner
import utilities.cerrarScanner
import java.util.*


/**
 *
 * @since 15/12/2024 v1.0.0
 * @author Oriol Callao, Yujiang Xia
 * ## Main
 * @param scan Abrir Scanner
 * @param menu Llamar la funcion del menu
 *
 */

fun main() {

    val scan: Scanner = abrirScanner()

    menu(scan)
}


/**
 * ## Menu
 * @param pila Lista mutable para guardar y eliminar los valores
 * @param opcion Variable para guardar opcion escogida
 * @param dowhile Se ejecutara el menu primero despues comprobara mientras que no sea num 4 se repetira
 * @param do Llama la funcion [pedirNumero] y ejecuta un condicional dependiendo que opcion se hara una serie de ejecuciones
 * @param [cerrarScanner] Cerramos escaner cuando salga del programa
 *
 */

fun menu(scan: Scanner) {

    // Creación de la pila
    val pila = mutableListOf<Int>()


    var opcion: Int
    do {
        opcion = pedirNumero(
            """
            Escoge alguna opción:
             1.- Añadir número
             2.- Quitar número
             3.- Mostrar contenido de la pila
             4.- Salir
            """.trimIndent(),
            scan
        )

        when (opcion) {
            1 -> op1("Introduce un número para añadir: ", scan, pila)
            2 -> op2(pila)
            3 -> op3(pila)
            4 -> println("Saliendo del programa...")
            else -> println("Opción no válida. Intenta nuevamente.")
        }
    } while (opcion != 4)

    cerrarScanner(scan)
}

/**
 *
 * ## PedirNumero
 * @param msg mostrara un mensaje
 * @param scan Leer valor del usuario
 * @return Devolvera el valor escaneado
 */

fun pedirNumero(msg: String, scan: Scanner): Int {
    print(msg)
    return scan.nextInt()
}


/**
 *
 * ## Opcion Numero 1 (PUSH)
 * @param msg muestra el enunciado de la opcion
 * @param scan Escanea el valor para despues usarlo en la ejecucuion que ha pedido el usuario
 * @param pila Uso de la lista creada anteriormente
 * @param ifelse Comprobara que la lista no este llena si no lo esta lo anyadira sino mostrara que esta llena
 *
 */

fun op1(msg: String, scan: Scanner, pila: MutableList<Int>) {

    if (pila.size < 10){
        print(msg)
        val num = scan.nextInt()
        pila.add(num)
        println("Número añadido.")
    }else{
        println("Lista llena, no se puede anyadir mas de 10 numeros.")
    }

}


/**
 *
 * ## Opcion Numero 2 (POP)
 * @param pila Usamos la lista para acceder el contenido
 * @param ifelse Comprobara primero que no este vacia, si no lo esta sacara el ultimo numero introducido
 */

fun op2(pila: MutableList<Int>) {
    if (pila.isNotEmpty()) {
        val removed = pila.removeAt(pila.size - 1)
        println("Número eliminado: $removed")
    } else {
        println("La pila está vacia, no se puede quitar ningun numero.")

    }
}

/**
 *
 * ## Opcion 3 (Println)
 *
 * @param pila Usamos lista para despues ser mostrado en pantalla
 * @param ifelse Comprobamos si esta vacio o no si lo esta saldra un mensaje que esta vacio
 *
 */

fun op3(pila: MutableList<Int>) {
    if (pila.isNotEmpty()) {
        println("Contenido de la pila: $pila")
    } else {
        println("La pila está vacía.")
    }
}