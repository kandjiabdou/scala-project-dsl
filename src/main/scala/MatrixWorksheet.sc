

// Example 1: Create and display matrices
val matrixA = Matrix(Vector(
  Vector(1.0, 2.0, 3.0),
  Vector(4.0, 5.0, 6.0),
  Vector(7.0, 8.0, 9.0)
))
val matrixB = Matrix(Vector(
  Vector(9.0, 8.0, 7.0),
  Vector(6.0, 5.0, 4.0),
  Vector(3.0, 2.0, 1.0)
))

println("Matrix A:")
println(matrixA)

println("\nMatrix B:")
println(matrixB)

// Example 2: Matrix addition
val addedMatrix = matrixA + matrixB
println("\nMatrix A + Matrix B:")
addedMatrix

// Example 3: Matrix subtraction
val subtractedMatrix = matrixA - matrixB
println("\nMatrix A - Matrix B:")
subtractedMatrix

// Example 4: Matrix multiplication
val multipliedMatrix = matrixA * matrixB.transpose
println("\nMatrix A * Transpose of Matrix B:")
multipliedMatrix

// Example 5: Determinant of a matrix
val determinant = matrixA.determinant
println("\nDeterminant of Matrix A:")
determinant

// Example 6: Transpose of a matrix
val transposedMatrix = matrixA.transpose
println("\nTranspose of Matrix A:")
transposedMatrix

// Example 7: RREF of a matrix
val rrefMatrix = matrixA.rref
println("\nRREF of Matrix A:")
rrefMatrix

// Example 8: Inverse of a matrix (if invertible)
val invertibleMatrix = Matrix(Vector(
  Vector(2.0, -1.0, 0.0),
  Vector(-1.0, 2.0, -1.0),
  Vector(0.0, -1.0, 2.0)
))
println("\nMatrix C (Invertible):\n")
invertibleMatrix

val inverse = invertibleMatrix.invert
println("\nInverse of Matrix C:")
inverse match {
  case Some(inv) => println(inv)
  case None      => println("Matrix is not invertible.")
}

// Example 9: Rotate a matrix
val rotatedMatrix = matrixA.rotateN(1)
println("\nMatrix A rotated 90 degrees clockwise:")
rotatedMatrix

// Example 10: Identity matrix
val identityMatrix = Matrix.identity(3)
println("\nIdentity Matrix (3x3):")
identityMatrix
