package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CesarPropiasTest extends AnyFunSuite {

  val c = new CifradosClasicos()
  import c._

  test("cesar propia: xyz con 3 da la vuelta al alfabeto y da abc") {
    assert(cesar("xyz", 3) == "abc")
  }

  test("cesar propia: abc con -1 da zab") {
    assert(cesar("abc", -1) == "zab")
  }

  test("cesar propia: con desplazamiento 0 el mensaje no cambia") {
    assert(cesar("programacion funcional", 0) == "programacion funcional")
  }

  test("cesar propia: -53 equivale a -1") {
    assert(cesar("hola", -53) == "gnkz")
  }

  test("cesar propia: caracteres fuera de a-z, como la ñ, no se cifran") {
    assert(cesar("año 2026: ¡hola!", 2) == "cñq 2026: ¡jqnc!")
  }
}
