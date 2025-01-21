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

  "A 1x1 matrix" should "have a determinant equal to its only element" in {
    val matrix = Matrix(Vector(Vector(5.0)))
    matrix.determinant shouldEqual 5.0
  }

  "A 2x2 matrix" should "calculate its determinant correctly" in {
    val matrix = Matrix(Vector(Vector(1.0, 2.0), Vector(3.0, 4.0)))
    matrix.determinant shouldEqual (1.0 * 4.0 - 2.0 * 3.0)
  }

  "A 3x3 matrix" should "calculate its determinant correctly" in {
    val matrix = Matrix(Vector(Vector(1, 0, 0), Vector(0, 1, 0), Vector(0, 0, 1)))
    matrix.determinant shouldEqual 1
  }

  "A larger matrix" should "calculate its determinant correctly using recursive expansion" in {
    val matrix = Matrix(Vector(Vector(6, 1, 1), Vector(4, -2, 5), Vector(2, 8, 7)))
    matrix.determinant shouldEqual -306
  }

  "A square matrix" should "be transposed correctly" in {
    val matrix = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    val expected = Matrix(Vector(Vector(1, 3), Vector(2, 4)))
    matrix.transpose shouldEqual expected
  }

  "A non-square matrix" should "be transposed correctly" in {
    val matrix = Matrix(Vector(Vector(1, 2, 3), Vector(4, 5, 6)))
    val expected = Matrix(Vector(Vector(1, 4), Vector(2, 5), Vector(3, 6)))
    matrix.transpose shouldEqual expected
  }

  "A single row matrix" should "be transposed into a single column matrix" in {
    val matrix = Matrix(Vector(Vector(1, 2, 3)))
    val expected = Matrix(Vector(Vector(1), Vector(2), Vector(3)))
    matrix.transpose shouldEqual expected
  }

  "A single column matrix" should "be transposed into a single row matrix" in {
    val matrix = Matrix(Vector(Vector(1), Vector(2), Vector(3)))
    val expected = Matrix(Vector(Vector(1, 2, 3)))
    matrix.transpose shouldEqual expected
  }

  "Matrix subtraction" should "subtract two matrices correctly" in {
    val matrix1 = Matrix(Vector(Vector(5, 6), Vector(7, 8)))
    val matrix2 = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    val expected = Matrix(Vector(Vector(4, 4), Vector(4, 4)))
    (matrix1 - matrix2) shouldEqual expected
  }

  it should "handle subtraction with zeros correctly" in {
    val matrix1 = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    val matrix2 = Matrix(Vector(Vector(0, 0), Vector(0, 0)))
    val expected = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    (matrix1 - matrix2) shouldEqual expected
  }

  it should "result in a zero matrix when a matrix is subtracted from itself" in {
    val matrix = Matrix(Vector(Vector(10, 20), Vector(30, 40)))
    val expected = Matrix(Vector(Vector(0, 0), Vector(0, 0)))
    (matrix - matrix) shouldEqual expected
  }

  it should "throw an IllegalArgumentException for matrices with different dimensions" in {
    val matrix1 = Matrix(Vector(Vector(1, 2)))
    val matrix2 = Matrix(Vector(Vector(1, 2, 3)))
    assertThrows[IllegalArgumentException] {
      matrix1 - matrix2
    }
  }

  "Matrix rotation" should "rotate the matrix 90 degrees n times correctly" in {
    val matrix = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    matrix.rotateN(1) shouldEqual Matrix(Vector(Vector(3, 1), Vector(4, 2)))
    matrix.rotateN(2) shouldEqual Matrix(Vector(Vector(4, 3), Vector(2, 1)))
    matrix.rotateN(3) shouldEqual Matrix(Vector(Vector(2, 4), Vector(1, 3)))
    matrix.rotateN(4) shouldEqual matrix
  }

  "Row swapping" should "swap rows correctly" in {
    val matrix = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    matrix.swapRows(0, 1) shouldEqual Matrix(Vector(Vector(3, 4), Vector(1, 2)))
  }

  "Trace calculation" should "calculate the trace of a square matrix" in {
    val matrix = Matrix(Vector(Vector(1, 2), Vector(3, 4)))
    matrix.trace shouldEqual 5.0
  }

  "Identity matrix" should "create a correct identity matrix of specified size" in {
    Matrix.identity(3) shouldEqual Matrix(Vector(Vector(1.0, 0.0, 0.0), Vector(0.0, 1.0, 0.0), Vector(0.0, 0.0, 1.0)))
  }

  "RREF transformation" should "reduce the matrix to reduced row echelon form correctly for a 3x4 matrix" in {
    val matrix = Matrix(Vector(
      Vector(1, 2, -1, 0),
      Vector(0, 2, 3, -1),
      Vector(2, 4, 0, 1)
    ))
    val expectedRREF = Matrix(Vector(
      Vector(1.0, 0.0, 0.0, 3.0),
      Vector(0.0, 1.0, 0.0, -1.25),   // -5/4
      Vector(0.0, 0.0, 1.0, 0.5)      // 1/2
    ))
    matrix.rref shouldEqual expectedRREF
  }

  "Matrix inversion" should "invert a matrix correctly" in {
    val matrix = Matrix(Vector(Vector(4, 7), Vector(2, 6)))
    matrix.invert shouldBe defined
    matrix.invert.get.rows.flatten.zip(Vector(Vector(0.6, -0.7), Vector(-0.2, 0.4)).flatten).foreach {
      case (actual, expected) => actual should be(expected +- 0.0001)
    }
  }


  it should "return None when the matrix is not invertible" in {
    val matrix = Matrix(Vector(Vector(1, 2, 3), Vector(4, 5, 6), Vector(7, 8, 9)))
    matrix.invert shouldBe None
  }
}