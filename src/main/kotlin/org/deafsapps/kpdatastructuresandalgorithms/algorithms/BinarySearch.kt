package org.deafsapps.kpdatastructuresandalgorithms.algorithms

/**
 * Binary search is one of the most efficient searching algorithms with a time
 * complexity of O(log(n)). This is comparable with searching for an element inside a
 * balanced binary search tree.
 * Two conditions need to be met before you can use binary search:
 * - The collection must be able to perform index manipulation in constant time.
 * - The collection must be sorted.
 */

fun <T : Comparable<T>> ArrayList<T>.binarySearch(
    value: T,
    range: IntRange = indices
): Int? {
    // Return 'null' if the 'range' doesn't contain at least one element
    if (range.first > range.last) return null

    val size: Int = range.last - range.first + 1
    val middle = range.first + size / 2

    return when {
        this[middle] == value -> middle
        this[middle] > value -> binarySearch(value = value, range = range.first until middle)
        else -> binarySearch(value, (middle + 1)..range.last)
    }

}

fun main() {

    val array: ArrayList<Int> = arrayListOf(1, 5, 15, 17, 19, 22, 24, 31, 105, 150)
    val search31: Int = array.indexOf(31)
    val binarySearch31: Int? = array.binarySearch(31)

    println("indexOf(): $search31")
    println("binarySearch(): $binarySearch31")

}