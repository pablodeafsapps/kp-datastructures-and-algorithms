package org.deafsapps.kpdatastructuresandalgorithms.algorithms

/**
 * Radix sort is a non-comparative algorithm for sorting integers in linear time. There
 * are many implementations of radix sort that focus on different problems. To keep
 * things simple, you’ll focus on sorting base 10 integers while investigating the least
 * significant digit (LSD) variant of radix sort.
 */

fun MutableList<Int>.radixSort() {
    // Setting up this example for base-10 numbers
    val base = 10
    // Tracking done status and buckets count
    var done = false
    var digits = 1
    while (!done) {
        done = true
        // Declare a base-10 buckets array
        val buckets = arrayListOf<MutableList<Int>>().apply {
            for(idx in 0 until base) {
                add(arrayListOf())
            }
        }
        // Let's place each number in the list in a specific bucket, according to the reminder
        forEach { number ->
            val remainingPart = number / digits
            val digit = remainingPart % base
            buckets[digit].add(number)
            if (remainingPart > 0) {
                done = false
            }
        }
        // To finish the iteration, 'digits' gets increased according to the base and the current list gets repopulated
        digits *= base
        clear()
        addAll(buckets.flatten())
    }
}

fun main() {
    val list = arrayListOf(88, 410, 1772, 20)
    println("Original: $list")
    list.radixSort()
    println("Radix sorted: $list")
}
