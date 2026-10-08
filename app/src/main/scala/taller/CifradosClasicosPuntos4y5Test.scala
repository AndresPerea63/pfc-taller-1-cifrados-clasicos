package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

/**
 * Casos de prueba propios de los puntos 4 y 5. Ninguno repite un ejemplo del
 * enunciado ni una de las pruebas que trae el material.
 */
@RunWith(classOf[JUnitRunner])
class CifradosClasicosPuntos4y5Test extends AnyFunSuite {

  val c = new CifradosClasicos()
  import c._

  // Punto 4: desplazamientoProbable y romperCesar -----------------------------

  test("desplazamientoProbable: si la más frecuente es la e, el desplazamiento es 0") {
    assert(desplazamientoProbable("eee") == 0)
  }

  test("desplazamientoProbable: la a está 22 después de la e") {
    assert(desplazamientoProbable("a") == 22)
  }

  test("desplazamientoProbable: la o está 10 después de la e") {
    assert(desplazamientoProbable("ooopp") == 10)
  }

  test("desplazamientoProbable: la z está 21 después de la e") {
    assert(desplazamientoProbable("zzz yy") == 21)
  }

  test("desplazamientoProbable: las mayúsculas y los signos no cuentan") {
    // Solo hay dos k minúsculas; las tres K mayúsculas se ignoran.
    assert(desplazamientoProbable("KKK, kk!! 44") == 6)
  }

  test("romperCesar: recupera un mensaje donde la e es la más frecuente") {
    val original = "ese es el eje de la tierra"
    assert(romperCesar(cesar(original, 11)) == original)
  }

  test("romperCesar: conserva espacios, signos y dígitos") {
    val original = "el cielo es el techo, eee! 2026"
    assert(romperCesar(cesar(original, 5)) == original)
  }

  test("romperCesar: si la más frecuente ya es la e, no cambia nada") {
    assert(romperCesar("eee ee") == "eee ee")
  }

  test("romperCesar: un mensaje sin letras sale igual") {
    assert(romperCesar("123 !?") == "123 !?")
  }

  test("romperCesar: falla con casa, porque su letra más frecuente es la a") {
    // El desplazamiento real es 3, pero fdvd tiene la d repetida y se estima 25.
    assert(romperCesar(cesar("casa", 3)) == "gewe")
    assert(romperCesar(cesar("casa", 3)) != "casa")
  }

  // Punto 5: combinaciones ----------------------------------------------------

  test("combinaciones: 4 letras sobre 3 dan 24") {
    assert(combinaciones(4, 3) == BigInt(24))
  }

  test("combinaciones: con una sola letra no hay mensajes de largo 5") {
    assert(combinaciones(5, 1) == BigInt(0))
  }

  test("combinaciones: con 2 letras solo se alternan, así que siempre hay 2") {
    assert(combinaciones(10, 2) == BigInt(2))
  }

  test("combinaciones: 2 letras sobre 26 dan 26 por 25") {
    assert(combinaciones(2, 26) == BigInt(650))
  }

  test("combinaciones: coincide con la forma cerrada a por (a - 1) a la n - 1") {
    assert(combinaciones(7, 10) == BigInt(10) * BigInt(9).pow(6))
  }

  test("combinaciones: aguanta números que no caben en un Long") {
    assert(combinaciones(30, 26) == BigInt(26) * BigInt(25).pow(29))
  }

  // Punto 5: vigenere ---------------------------------------------------------

  test("vigenere: con la clave b cada letra avanza una posición") {
    assert(vigenere("abc", "b") == "bcd")
  }

  test("vigenere: con la clave a el mensaje no cambia") {
    assert(vigenere("hola", "a") == "hola")
  }

  test("vigenere: al pasarse de la z se vuelve a la a") {
    assert(vigenere("xyz", "c") == "zab")
  }

  test("vigenere: la clave se repite hasta cubrir el mensaje") {
    assert(vigenere("zebra", "abc") == "zfdrb")
  }

  test("vigenere: el espacio se copia y no gasta letra de la clave") {
    assert(vigenere("ab ab", "bc") == "bd bd")
  }

  test("vigenere: un mensaje sin letras sale igual") {
    assert(vigenere("123 !?", "abc") == "123 !?")
  }

  test("vigenere: una mayúscula se copia y no gasta letra de la clave") {
    assert(vigenere("Ab", "bc") == "Ac")
  }

  test("vigenere: una clave más larga que el mensaje solo usa sus primeras letras") {
    assert(vigenere("ab", "zzzz") == "za")
  }

  test("vigenere: con la clave complementaria se descifra") {
    // La clave abc se deshace con azy: 0, 26 - 1 y 26 - 2 posiciones.
    val original = "mensaje secreto"
    assert(vigenere(vigenere(original, "abc"), "azy") == original)
  }
}
