# **Matrix DSL in Scala 3**  
A **Functional Programming** and **DSL** Project  

---

## **Table of Contents**  
1. [Project Overview](#project-overview)  
2. [Domain Justification](#domain-justification)  
3. [DSL Design and Functional Principles](#dsl-design-and-functional-principles)  
4. [Features](#features)  
5. [Usage Examples](#usage-examples)  
    
   - [Basic Arithmetic Operations](#basic-arithmetic-operations)
   -  [Creating a Matrix](#creating-a-matrix) 
   - [Row Echelon Form (RREF)](#row-echelon-form-rref)  
   - [Matrix Inversion](#matrix-inversion)  
   - [Other Operations](#other-operations)  
6. [Testing](#testing)  
7. [Project Structure & Build Instructions](#project-structure--build-instructions)  
 

---

## **Project Overview**  
This project implements a **Matrix DSL** in Scala 3, inspired by the final project requirement from the **Functional Programming in Scala 3 - Building a DSL** curriculum. The primary goal is to showcase functional programming principles through **immutable**, **type-safe**, and **composable** matrix operations.

### **Goals**  
- Provide a **clean**, **declarative** API for matrix operations.  
- Demonstrate **functional programming** best practices (immutability, pure functions, expressive types).  
- Offer a DSL that makes matrix manipulation **intuitive** and **concise**.  

---

## **Domain Justification**  
**Linear Algebra** is a crucial domain in mathematics and software engineering. It underpins fields such as **machine learning**, **computer graphics**, **engineering simulations**, and more. A **DSL** for matrix operations can significantly simplify expressing complex transformations, such as matrix multiplications, solving systems of linear equations, and computing inverses—all in a **readable, high-level** manner.  

By designing an internal DSL in Scala 3, we harness:
- **Expressive Syntax**: Clear and concise mathematical operations.  
- **Strong Typing**: Ensures operations remain correct and safe.  
- **Functional Paradigms**: Guarantees immutability and composability.  

---

## **DSL Design and Functional Principles**  
1. **Immutable Data Structures**  
   - All matrix operations return new `Matrix` instances rather than mutating existing ones.  

2. **Pure Functions**  
   - Matrix methods do not produce side effects; they always return the result of the operation on the input matrix.  

3. **Composability**  
   - Methods can be chained or composed, allowing operations like `(A + B).rref` or `A.rotateN(2).invert` to be easily expressed.  

4. **Scala 3 Enhancements**  
   - Leverage concise syntax, pattern matching, and advanced type features where appropriate.  

---

## **Features**  
- **Matrix Creation**  
  Create matrices from two-dimensional `Vector` structures.  
- **Addition, Subtraction, Multiplication**  
  Standard arithmetic operations with enforced dimension checks.  
- **Rotation**  
  Rotate a matrix by 90-degree increments (`rotate90`) or by any multiple of 90° (`rotateN`).  
- **RREF (Reduced Row Echelon Form)**  
  Implement a standard algorithm to transform a matrix into its RREF.  
- **Inversion**  
  Compute the inverse of a square matrix, if it exists.  
- **Transpose**  
  Swap rows and columns of a matrix.  
- **Determinant**  
  Recursively compute determinant values for NxN matrices.  
- **Trace**  
  Sum the main diagonal for square matrices.  

---

## **Usage Examples**  

### **Basic Arithmetic Operations**

```scala
val matA = Matrix(Vector(
  Vector(1, 2),
  Vector(3, 4)
))

val matB = Matrix(Vector(
  Vector(5, 6),
  Vector(7, 8)
))

val sum = matA + matB
// sum => [ 6  8 ]
//         [10 12]

val diff = matA - matB
// diff => [ -4  -4 ]
//          [ -4  -4 ]

val product = matA * matB
// product => [19 22]
//            [43 50]
```
### **Creating a Matrix**
```scala
import MatrixDSL.Matrix

// 3x3 Matrix
val mat = Matrix(Vector(
  Vector(1.0, 2.0, 3.0),
  Vector(4.0, 5.0, 6.0),
  Vector(7.0, 8.0, 9.0)
))
println(mat)
// [1.0 2.0 3.0]
// [4.0 5.0 6.0]
// [7.0 8.0 9.0]
```
### **Row Echelon Form (RREF)**

```scala
val matC = Matrix(Vector(
  Vector(1, 2, -1, 0),
  Vector(0, 2, 3, -1),
  Vector(2, 4, 0, 1)
))

val matCRREF = matC.rref
// matCRREF => [1.0  0.0  0.0   3.0   ]
//             [0.0  1.0  0.0  -1.25 ]
//             [0.0  0.0  1.0   0.5  ]
```
### Matrix Inversion
```scala
val matD = Matrix(Vector(
  Vector(1, 2),
  Vector(3, 4)
))

val invD = matD.invert
invD match {
  case Some(inverse) => println(inverse)
  case None          => println("Matrix is not invertible")
}
// [ -2.0   1.0 ]
// [  1.5  -0.5 ]
```
### Other Operations
```scala
val matE = Matrix(Vector(
  Vector(1.0, 2.0),
  Vector(3.0, 4.0)
))

// Rotate matrix by 90 degrees
val rotated = matE.rotate90

// Rotate matrix by n * 90 degrees
val rotatedTwice = matE.rotateN(2)

// Transpose a matrix
val transposed = matE.transpose

// Determinant of a 2x2 matrix
val detE = matE.determinant // => (1*4) - (2*3) = -2

// Trace of a matrix (sum of diagonal elements)
val traceE = matE.trace // => 1 + 4 = 5
```
### Testing
```bash
// Run tests:
Navigate to the project root in your terminal.
Use sbt test to execute all tests.
```
### Project Structure & Build Instructions
```plaintext
MatrixDSL/
 ├─ src/
 │   └─ main/scala/MatrixDSL/
 │       └─ Matrix.scala          // Core DSL Implementation
 ├─ test/
 │   └─ scala/MatrixDSL/
 │       └─ MatrixSpec.scala      // Unit Tests
 ├─ build.sbt                     // SBT Build Definition
 └─ README.md                     // Project Documentation
```
### Build

Ensure you have sbt installed on your machine. Clone this repository:
```bash
git clone https://github.com/kandjiabdou/scala-project-dsl.git
```
Enter the project directory and run:
```bash
sbt compile
sbt test
```

