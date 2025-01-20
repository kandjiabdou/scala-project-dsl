case class Matrix(rows: Vector[Vector[Double]]) {
  require(rows.nonEmpty, "Matrix must have at least one row")
  require(rows.forall(_.length == rows.head.length), "All rows must have the same length")

  def +(other: Matrix): Matrix = {
    require(rows.length == other.rows.length && rows.head.length == other.rows.head.length, "Matrix dimensions must match")
    Matrix(rows.zip(other.rows).map { case (r1, r2) => r1.zip(r2).map { case (x, y) => x + y } })
  }

  def *(other: Matrix): Matrix = {
    require(rows.head.length == other.rows.length, "Number of columns in A must equal number of rows in B")
    val transposedOther = other.transpose
    Matrix(rows.map(row => transposedOther.rows.map(col => row.zip(col).map { case (x, y) => x * y }.sum)))
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