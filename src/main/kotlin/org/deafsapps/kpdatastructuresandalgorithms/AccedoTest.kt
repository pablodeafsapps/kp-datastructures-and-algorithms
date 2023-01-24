package org.deafsapps.kpdatastructuresandalgorithms

import kotlin.math.pow

fun solution(A: IntArray, B: IntArray, X: Int, Y: Int): Int {
    // Implement your solution here

    val diffArray: DoubleArray = DoubleArray(A.size)
    for (idx in A.indices) {
        diffArray[idx] = (A[idx] - X).toDouble().pow(2) + (B[idx] - Y).toDouble().pow(2)
    }

    println(diffArray.toList())

    return diffArray.indexOfFirst { it <= 20.0.pow(2) }
}

fun solution(N: Int): Int {
    // Implement your solution here
    val stringN: String = N.toString()
    val excludedPosition: Int = if (N > 0) {
        stringN.indexOfFirst { it == '5' }
    } else {
        stringN.indexOfLast { it == '5' }
    }

    return stringN.removeRange(startIndex = excludedPosition, endIndex = excludedPosition + 1).toInt()
}

fun main() {

    val testA: IntArray = intArrayOf(100, 200, 100)
    val testB: IntArray = intArrayOf(50, 100, 100)

    val result1 = solution(A = testA, B = testB, X = 100, Y = 70)
    println(result1)


    val testN = 15958

    val result2 = solution(N = testN)
    println(result2)

}
