package controllers

import java.util.*

data class billete(
    var tipo: Double,  // Cambiado de Int a Double
    var zona: Int
)

fun main() {
    val scan = abrirscanner()
    val cesta = mutableListOf<billete>()

    var continuar = true
    while (continuar) {
        val opcion = menu(scan)
        if (opcion == -1) {
            continuar = false
        } else {
            when (opcion) {
                1 -> pedirzona("Elige una zona para el Billet Senzill (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                2 -> pedirzona("Elige una zona para el TCasual (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                3 -> pedirzona("Elige una zona para el TUsual (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                4 -> pedirzona("Elige una zona para el TFamiliar (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                5 -> pedirzona("Elige una zona para el TJove (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                else -> {
                    println("Opcion no valida. Intenta de nuevo.")
                    continuar = false
                }
            }
        }
    }

    cerrarscanner(scan)
}

fun abrirscanner(): Scanner {
    return Scanner(System.`in`).useLocale(Locale.UK)
}

fun cerrarscanner(scanner: Scanner) {
    scanner.close()
}

fun cerrarprogr(num: Int) {
    println("Codigo secreto correcto, programa apagado.")
}

fun menu(scan: Scanner): Int {
    var opcion = pedirnumero(
        """
        Escoge alguna opcion:
         1.- Bitllet senzill
         2.- TCasual
         3.- TUsual
         4.- TFamiliar
         5.- TJove
        """.trimIndent(),
        scan
    )
    if (opcion == 4321) {
        cerrarprogr(opcion)
        return -1
    }
    while (opcion !in 1..5) {
        println("Opcion no valida. Escoge una opcion de las anteriores (1-5).")
        opcion = scan.nextInt()
    }
    return opcion
}

fun pedirnumero(msg: String, scan: Scanner): Int {
    println(msg)
    return scan.nextInt()
}

fun pedirtipo(scan: Scanner): Double {
    println("Selecciona el tipo de billete:")
    println("1.- Bitllet senzill (2.40€)")
    println("2.- TCasual (11.35€)")
    println("3.- TUsual (40.00€)")
    println("4.- TFamiliar (10.00€)")
    println("5.- TJove (80.00€)")

    var tipo: Double
    var opcion = scan.nextInt()

    while (opcion !in 1..5) {
        println("Opcion no valida. Escoge una opcion de las anteriores (1-5).")
        opcion = scan.nextInt()
    }

    tipo = when (opcion) {
        1 -> 2.40
        2 -> 11.35
        3 -> 40.00
        4 -> 10.00
        5 -> 80.00
        else -> 0.0
    }
    return tipo
}

fun pedirzona(msg: String, scan: Scanner, cesta: MutableList<billete>) {
    var seguiranyadiendo = true

    while (seguiranyadiendo) {
        val tipo = pedirtipo(scan) // Aquí pedimos el tipo del billete

        println(msg)
        var opcion = scan.nextInt()

        // Consumir la línea residual después de leer nextInt
        scan.nextLine()

        if (opcion == 0) {
            println("Volviendo al menu principal...")
            return
        } else if (opcion in 1..3) {
            cesta.add(billete(tipo = tipo, zona = opcion))
            println("Billete añadido: Tipo $tipo, Zona $opcion")

            if (cesta.size < 3) {
                println("¿Quieres añadir otro billete? (s/n)")
                val respuesta = scan.nextLine().trim()
                if (respuesta == "n") {
                    println("Calculando total...")
                    calculo(cesta, scan)
                    seguiranyadiendo = false // Finaliza el bucle si no quiere añadir más billetes
                }
            } else {
                println("Has alcanzado el limite de 3 billetes. Finalizando compra...")
                calculo(cesta, scan)
                seguiranyadiendo = false // Finaliza el bucle si ya hay 3 billetes
            }
        } else {
            println("Zona no valida. Intenta de nuevo.")
        }
    }
}

// Calculo de billetes con las zonas
fun calculo(cesta: MutableList<billete>, scan: Scanner) {
    var total = 0.00
    for (billete in cesta) {
        val extra = when (billete.zona) {
            1 -> 1.0
            2 -> 1.3125
            3 -> 1.8443
            else -> 0.0
        }
        total += extra * billete.tipo
    }
    println("El total de la compra es: $total €")
    // No imprimimos el tiquet aquí, solo calculamos el total
    pagar(cesta, total, scan)
}

// Función para imprimir el tiquet
fun imprimirtiquet(cesta: MutableList<billete>, total: Double) {
    println("\n_____TIQUET_____")
    cesta.forEach { billete ->
        println("Billete Tipo ${billete.tipo}, Zona ${billete.zona}")
    }
    println("Total: $total €")
    println("________________")
    println("Recoge tu tiquet y pago.")
}

// Función para calcular el cambio
fun calcularcambio(total: Double, dinerointroducido: Double): Double {
    return dinerointroducido - total
}

// Función para gestionar el pago
fun pagar(cesta: MutableList<billete>, total: Double, scan: Scanner) {
    val dineroValido = arrayOf(0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0, 200.0, 500.0) // Monedas y billetes válidos
    var dinerointroducido = 0.0
    while (dinerointroducido < total) {
        println("Introduce el dinero para pagar el total ($total €).")
        val dinero = scan.nextDouble()

        // Consumir la línea residual después de leer nextDouble
        scan.nextLine()

        // Validación para asegurarse de que el dinero introducido es válido
        if (dinero in dineroValido) {
            dinerointroducido += dinero
            if (dinerointroducido < total) {
                println("Te falta ${total - dinerointroducido} € para completar el pago.")
            }
        } else {
            println("Por favor, introduce una cantidad válida (monedas o billetes permitidos).")
        }
    }
    val cambio = calcularcambio(total, dinerointroducido) // Calcular el cambio
    println("Tu cambio es: $cambio €")

    // Preguntar si quiere imprimir el tiquet
    println("¿Quieres imprimir el tiquet? (s/n)")
    val respuesta = scan.nextLine().trim()

    if (respuesta == "s") {
        imprimirtiquet(cesta, total)
    } else {
        println("Gracias por utilizar nuestro servicio. ¡Hasta luego!")
    }
}


