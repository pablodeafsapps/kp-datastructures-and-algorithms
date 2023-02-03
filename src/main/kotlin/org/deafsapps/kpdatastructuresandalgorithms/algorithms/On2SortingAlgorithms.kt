package org.deafsapps.kpdatastructuresandalgorithms.algorithms

/**
 * O(n²) time complexity is not great performance, but the sorting algorithms in this
 * category are easy to understand and useful in some scenarios. These algorithms are
 * space efficient, and they only require constant O(1) additional memory space. For
 * small data sets, these sorts compare favorably against more complex sorts. It’s
 * usually not recommended to use O(n²) in production code, but you’ll need to start
 * somewhere, and these algorithms are a great place to start.
 *
 * All of these are comparison-based sorting methods. In other words, they rely on a
 * comparison method, such as the less than operator, to order elements. You measure
 * a sorting technique’s general performance by counting the number of times this
 * comparison gets called.
 */

/**
 * The bubble sort repeatedly compares adjacent values and swaps them, if needed, to
 * perform the sort. The larger values in the set will, therefore, bubble up to the end
 * of the collection.
 */
fun <T : Comparable<T>> MutableList<T>.bubbleSort1(
    showPasses: Boolean = false
): MutableList<T> = apply {
    if (size < 2) return@apply
    /**
     * A single pass will bubble the largest value to the end of the collection. Every
     * pass needs to compare one less value than in the previous pass, so the array is
     * shortened by one each pass.
     */
    for (end in this.lastIndex downTo 1) {
        var swapped: Boolean = false
        for (idx in 0 until end) {
            if (this[idx] > this[idx + 1]) {
                swapWithNext(idx)
                swapped = true
            }
        }
        if (showPasses) println(this)
        if (!swapped) {
            return@apply
        }
    }
}

fun <T : Comparable<T>> MutableList<T>.bubbleSort2(
    showPasses: Boolean = false
): MutableList<T> = apply {
    if (size < 2) return@apply
    do {
        var swapped: Boolean = false
        for (idx in 0 until this.lastIndex) {
            if (this[idx] > this[idx + 1]) {
                swapWithNext(idx)
                swapped = true
            }
        }
        if (showPasses) println(this)
    } while (swapped)
}

/**
 * Selection sort follows the basic idea of bubble sort but improves upon this
 * algorithm by reducing the number of swap operations performed. Selection sort
 * only swaps at the end of each pass.
 */
fun <T : Comparable<T>> MutableList<T>.selectionSort(
    showPasses: Boolean = false
): MutableList<T> = apply {
    if (size < 2) return@apply
    // There's no point in including the last element, because if all others are in correct order, so is the last one
    for (current in 0 until this.lastIndex) {
        var lowest = current
        // In every pass, you go through the reminder of the collection and find the element with the lowest value
        for (other in (current + 1) until this.size) {
            if (this[other] < this[lowest]) {
                lowest = other
            }
        }
        // Only swap elements if there's anyone lower than 'current'
        if (lowest != current) {
            swapAt(lowest, current)
        }
        if (showPasses) println(this)
    }
}

/**
 * Like bubble sort and selection sort, insertion sort has an average time complexity of O(n²),
 * but the performance of insertion sort can vary. The more the data is already sorted, the less
 * work it needs to do. Insertion sort has a best time complexity of O(n) if the data is already
 * sorted.
 */
fun <T : Comparable<T>> MutableList<T>.insertionSort(
    showPasses: Boolean = false
): MutableList<T> = apply {
    if (size < 2) return@apply
    // Iterates from left to right, not including the first element (nothing to compare it with)
    for (current in 1 until this.size) {
        // Run backward from the current index so that you can shift left as needed
        for (shifting in (1..current).reversed()) {
            if (this[shifting] < this[shifting - 1]) {
                swapAt(first = shifting, shifting - 1)
            } else {
                // Break the inner loop, since 'current' is already in place
                break
            }
        }

        if (showPasses) println(this)
    }
}

fun <T : Comparable<T>> MutableList<T>.reverse(): MutableList<T> = apply {
    var leftIndex: Int = 0
    var rightIndex: Int = lastIndex

    while (leftIndex < rightIndex) {
        swapAt(first = leftIndex, second = rightIndex)
        leftIndex++
        rightIndex--
    }
}

private fun <T : Any> MutableList<T>.swapWithNext(idx: Int): MutableList<T> = swapAt(first = idx, second = idx + 1)

private fun <T : Any> MutableList<T>.swapAt(first: Int, second: Int): MutableList<T> = apply {
    val aux: T = this[first]
    this[first] = this[second]
    this[second] = aux
}

fun main() {
    val sampleList = arrayListOf(9, 4, 10, 3)
    val sampleList1 = arrayListOf(9, 4, 10, 3)
    val sampleList2 = arrayListOf(9, 4, 10, 3)
    val sampleList3 = arrayListOf(9, 4, 10, 3)
    val sampleList4 = arrayListOf(9, 4, 10, 3)
    val sampleList5 = arrayListOf(9, 4, 10, 3)
    println("Original: $sampleList")
    // BubbleSort1 example
    println("Bubble sorted (1): ${sampleList1.bubbleSort1(showPasses = true)}")
    // BubbleSort2 example
    println("Bubble sorted (2): ${sampleList2.bubbleSort2(showPasses = true)}")
    // SelectionSort example
    println("Selection sorted: ${sampleList3.selectionSort(showPasses = true)}")
    // InsertionSort example
    println("Insertion sorted: ${sampleList4.insertionSort(showPasses = true)}")
    // Reverse example
    println("Reverse: ${sampleList5.reverse()}")
}
