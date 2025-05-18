package org.deafsapps.kpdatastructuresandalgorithms.algorithms

/**
 * Merge sort is one of the most efficient sorting algorithms. With a time complexity of
 * O(n log n), it’s one of the fastest of all general-purpose sorting algorithms. The idea
 * behind merge sort is divide and conquer — to break a big problem into several
 * smaller, easier-to-solve problems, and then combine those solutions into a final
 * result. The merge sort mantra is to split first and merge after.
 *
 * The Merge sort consists of two main steps: split and merge.
 */
fun <T : Comparable<T>> List<T>.mergeSort(): List<T> {
    //--- Split ---
    // Recursion needs a "base case", which here is when the list only has one element
    if (size < 2) return this
    val middle = size / 2
    // Keep spliting recursively until there's only one element in the list ("base case")
    val left = subList(fromIndex = 0, toIndex = middle).mergeSort()
    val right = subList(fromIndex = middle, toIndex = size).mergeSort()
    //--- Merge ---
    return merge(left = left, right = right, verbosity = true)
}

private fun <T : Comparable<T>> merge(left: List<T>, right: List<T>, verbosity: Boolean = false): List<T> {
    var leftIndex = 0
    var rightIndex = 0
    var result = mutableListOf<T>()

    if (verbosity) {
        println("\n--- Merge Step ---")
        println("Left list: $left")
        println("Right list: $right")
    }

    while (leftIndex < left.size && rightIndex < right.size) {
        val leftElement = left[leftIndex]
        val rightElement = right[rightIndex]

        if (verbosity) {
            println("Left element: $leftElement, Right element: $rightElement")
        }

        when {
            leftElement < rightElement -> {
                result.add(leftElement)
                leftIndex++
            }
            rightElement < leftElement -> {
                result.add(rightElement)
                rightIndex++
            }
            else -> {
                result.addAll(listOf(leftElement, rightElement))
                leftIndex++
                rightIndex++
            }
        }
    }
    // if list candidates are not equally large, there are still remaining elements in the largest one
    if (leftIndex < left.size) {
        result.addAll(left.subList(leftIndex, left.size))
    }
    if (rightIndex < right.size) {
        result.addAll(right.subList(rightIndex, right.size))
    }
    if (verbosity) {
        println("Partial result: $result")
    }
    return result
}

fun main() {
    println("Merge-Sort example")
    val list = listOf(7, 2, 6, 3, 9)
    println("Original: $list")
    val result1 = list.mergeSort()
    println("Merge sorted 1: $result1")
//    val result2 = merge(left = list.subList(0, list.size/2), right = list.subList(list.size/2, list.size), verbosity = true)
//    println("Merge sorted 2: $result2")
}
