package org.deafsapps.kpdatastructuresandalgorithms.algorithms

/**
 * 'Quick Sort' is another comparison-based sorting algorithm. Much like 'merge sort', it
 * uses the same strategy of divide and conquer. One important feature of 'quick sort' is
 * choosing a pivot point. The pivot divides the list into three partitions:
 * [ elements < pivot | pivot | elements > pivot ]
 */

fun<T: Comparable<T>> List<T>.quicksortNaive(): List<T> {
    if (this.size < 2) return this
    val pivot = this[size / 2]
    val less = filter { it < pivot }
    val equal = filter { it == pivot }
    val greater = filter { it > pivot }
    return less.quicksortNaive() + equal + greater.quicksortNaive()
}

fun main() {
    val list = arrayListOf(12, 0, 3, 9, 2, 18, 8, 27, 1, 5, 8, -1, 21)
    println("Original: $list")
    list.quicksortNaive()
    println("Quick sorted: ${list.quicksortNaive()}")
}
