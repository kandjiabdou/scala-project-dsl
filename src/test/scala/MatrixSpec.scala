import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MatrixSpec extends AnyFlatSpec with Matchers {

  "Matrix addition" should "add two matrices correctly" in {
    val m1 = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    val m2 = Matrix(Vector(Vector(5, 6), Vector(7, 8)))
    val result = m1 + m2
    result shouldEqual Matrix(Vector(Vector(6, 8), Vector(10, 12)))
  }

  "Matrix multiplication" should "multiply two matrices correctly" in {
    val m1 = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    val m2 = Matrix(Vector(Vector(2, 0), Vector(1, 2)))
    val result = m1 * m2
    result shouldEqual Matrix(Vector(Vector(4, 4), Vector(10, 8)))
  }

  "Matrix determinant" should "compute the determinant of a square matrix" in {
    val m = Matrix(Vector(Vector(1, 2, 3), Vector(0, 1, 4), Vector(5, 6, 0)))
    m.determinant shouldEqual 1
  }

  "Identity matrix" should "be created correctly" in {
    val identity = Matrix.identity(3)
    identity shouldEqual Matrix(Vector(Vector(1, 0, 0), Vector(0, 1, 0), Vector(0, 0, 1)))
  }
}