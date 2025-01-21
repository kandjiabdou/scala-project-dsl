import scala.annotation.targetName
import scala.util.control.Breaks.*

case class Matrix(rows: Vector[Vector[Double]]) {
  require(rows.nonEmpty, "Matrix must have at least one row")
  require(rows.forall(_.length == rows.head.length), "All rows must have the same length")

  @targetName("addMatrices")
  def +(other: Matrix): Matrix = {
    require(rows.length == other.rows.length && rows.head.length == other.rows.head.length, "Matrix dimensions must match")
    Matrix(rows.zip(other.rows).map { case (r1, r2) => r1.zip(r2).map { case (x, y) => x + y } })
  }

  @targetName("subtractMatrices")
  def -(other: Matrix): Matrix = {
    require(rows.length == other.rows.length && rows.head.length == other.rows.head.length, "Matrix dimensions must match for subtraction")
    Matrix(rows.zip(other.rows).map { case (r1, r2) => r1.zip(r2).map { case (x, y) => x - y } })
  }

  @targetName("multiplyMatrices")
  def *(other: Matrix): Matrix = {
    require(rows.head.length == other.rows.length, "Number of columns in A must equal number of rows in B")
    val transposedOther = other.transpose
    Matrix(rows.map(row => transposedOther.rows.map(col => row.zip(col).map { case (x, y) => x * y }.sum)))
  }

  private def rotate90: Matrix = Matrix(rows.head.indices.map(i => rows.map(_(i)).reverse).toVector)

  def rotateN(n: Int): Matrix = {
    val times = (n % 4 + 4) % 4 // Normalize n to a positive number between 0 and 3
    (0 until times).foldLeft(this)((acc, _) => acc.rotate90)
  }

  def swapRows(x1: Int, x2: Int): Matrix = {
    require(x1 >= 0 && x1 < rows.length && x2 >= 0 && x2 < rows.length, "Row indices out of range")
    val swappedRows = rows.updated(x1, rows(x2)).updated(x2, rows(x1))
    Matrix(swappedRows)
  }

  def trace: Double = {
    require(rows.length == rows.head.length, "Matrix must be square to compute trace")
    rows.indices.map(i => rows(i)(i)).sum
  }

  def rref: Matrix = {
    var lead = 0
    val rowCount = rows.length
    val colCount = rows.head.length
    var mat = rows.map(_.toVector)

    breakable {
      for (r <- 0 until rowCount) {
        if (colCount <= lead) {
          break // break out of the loop
        }

        var i = r
        while (mat(i)(lead) == 0) {
          i += 1
          if (i == rowCount) {
            i = r
            lead += 1
            if (colCount == lead) {
              break // break out of the loop
            }
          }
        }

        // Swap the current row with the row having non-zero element at the current lead position
        var temp = mat(i)
        mat = mat.updated(i, mat(r))
        mat = mat.updated(r, temp)

        // Normalize the row by its leading coefficient
        temp = mat(r).map(_ / mat(r)(lead))
        mat = mat.updated(r, temp)

        for (j <- 0 until rowCount) {
          if (j != r) {
            val scale = mat(j)(lead)
            mat = mat.updated(j, mat(j).zip(mat(r)).map { case (x, y) => x - scale * y })
          }
        }
        lead += 1
      }
    }

    Matrix(mat)
  }


  def invert: Option[Matrix] = {
    require(rows.length == rows.head.length, "Matrix must be square to compute inverse")
    val n = rows.length
    val augmented = rows.zipWithIndex.map { case (row, i) =>
      row ++ Vector.tabulate(n)(j => if (i == j) 1.0 else 0.0)
    }

    val rrefMatrix = Matrix(augmented).rref

    if (rrefMatrix.rows.exists(row => row.take(n).forall(_ == 0))) None
    else Some(Matrix(rrefMatrix.rows.map(_.drop(n))))
  }

  def transpose: Matrix = Matrix(rows.head.indices.map(i => rows.map(_(i))).toVector)

  def determinant: Double = {
    require(rows.length == rows.head.length, "Matrix must be square to compute determinant")
    rows.length match {
      case 1 => rows.head.head
      case 2 => rows(0)(0) * rows(1)(1) - rows(0)(1) * rows(1)(0)
      case _ =>
        rows.head.indices.map { col =>
          val subMatrix = Matrix(rows.tail.map(row => row.zipWithIndex.collect { case (value, idx) if idx != col => value }))
          rows.head(col) * subMatrix.determinant * (if (col % 2 == 0) 1 else -1)
        }.sum
    }


  }

  override def toString: String = rows.map(_.mkString("[", " ", "]")).mkString("\n")
}

object Matrix {
  def identity(size: Int): Matrix = {
    require(size > 0, "Size must be positive")
    Matrix(Vector.tabulate(size, size)((i, j) => if (i == j) 1.0 else 0.0))
  }



}