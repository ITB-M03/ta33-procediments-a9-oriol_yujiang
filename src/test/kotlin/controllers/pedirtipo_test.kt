package controllers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utilities.abrirScanner

class pedirtipo_test(){


    @Test
    fun testPedirtipoConVariasEntradasInvalidas() {
        val input = "6\n0\n4\n"  // Primer input inválido "6", luego "0", luego "4" para TFamiliar
        val scanner = abrirScanner(input)
        val resultado = pedirtipo(scanner)
        assertEquals(10.00, resultado, 0.0)  // Verificar que devuelve 10.00 para TFamiliar
    }

}