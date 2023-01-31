package org.deafsapps.kpdatastructuresandalgorithms

import java.util.Collections
import kotlin.collections.ArrayList

interface Collection<T : Any> {

    val count: Int

    val isEmpty: Boolean
        get() = count == 0

    fun insert(element: T)

    fun remove(): T?

    fun remove(index: Int): T?

}

/**
 * A 'Heap' data structure is a complete binary tree data structure also known as a binary heap can be
 * constructed using an array. Heaps come in two flavors:
 * - Maxheap, in which elements with higher value have a higher priority
 * - Minheap, in which elements with a lower value have a higher priority
 *
 * Rule: In ha maxheap, parent nodes must always contain a value that is greater than or equal to any
 * children value. In a minheap, parent nodes must always contain a value that is less than or equal to
 * any children value.
 *
 * Some useful applications of a heap include:
 * - Calculating the minimum or maximum element of a collection
 * - Heap sort
 * - Implementing a priority queue
 * - Supporting graph algorithms, like Prim’s or Dijkstra’s, with a priority queue
 *
 * Heap Data Structure
 * - remove -> O(log(n))
 * - insert -> O(log(n))
 * - search -> O(n)
 * - peek -> O(1)
 */
interface Heap<T : Any> : Collection<T> {

    fun peek(): T?

}

abstract class AbstractHeap<T : Any>() : Heap<T> {

    /**
     * A heap may be represented as an array, knowing that each zero-indexed level spans for 2 * (idx + 1) positions
     */
    var elements: ArrayList<T> = ArrayList()

    override val count: Int
        get() = elements.size

    override fun peek(): T? = elements.firstOrNull()

    /**
     * Appends the element to the array and performs a sift up, which swaps the current
     * node with its parent, as long as the node has a higher priority than its parent.
     * The overall complexity of 'insert()' is O(log(n)), since appending takes O(1), while
     * sifting up elements in a heap takes O(log(n)).
     */
    override fun insert(element: T) {
        elements.add(element)
        siftUp(index = count - 1)
    }

    /**
     * A basic remove operation removes the root node from the heap, i.e. removing the maximum value
     * at the root node. To do so, the 'root' node gets swapped with the last element, and then sifted
     * down subsequently until the max/min heap rule is fulfilled.
     * The overall complexity of 'remove()' is O(log(n)), since swapping elements in an array takes only
     * O(1), while sifting down elements in a heap takes O(log(n)) time.
     */
    override fun remove(): T? {
        if (isEmpty) return null
        Collections.swap(elements, 0, count - 1)
        val item = elements.removeAt(count - 1)
        siftDown(index = 0)
        return item
    }

    /**
     * To remove any element from the heap, you need an index
     */
    override fun remove(index: Int): T? {
        // Check whether the index is within the bounds of the array
        if (index >= count) return  null
        return if (index == count - 1) {
            // If you are removing the last element in the heap, you don't need to do anything special
            elements.removeAt(count - 1)
        } else {
            /**
             * If you're not removing the last element, first swap the element with the last element and
             * remove it
             */
            Collections.swap(elements, index, count - 1)
            val item = elements.removeAt(count - 1)
            /**
             * Finally, perform both sift down and sift up to adjust the heap
             */
            siftDown(index = index)
            siftUp(index = index)
            item
        }
    }

    abstract fun compare(a: T, b: T): Int

    /**
     * Creating a heap out of an existing array is an operation usually called 'heapify'. This
     * looks similar to when creating 'AVLTree's out of 'Binary Search Tree's.
     */
    protected fun heapify(values: ArrayList<T>) {
        elements = values
        if (!isEmpty) {
            /**
             * Only loop through half of the elements because there's no point in sifting down
             * leaf nodes, only parent nodes
             */
            ((count / 2) downTo 0).forEach {
                siftDown(index = it)
            }
        }
    }

    private fun leftChildIndex(index: Int): Int = (2 * index) + 1

    private fun rightChildIndex(index: Int): Int = (2 * index) + 2

    private fun parentIndex(index: Int): Int = (index - 1) /  2

    private fun siftUp(index: Int) {
        var child: Int = index
        var parent: Int = parentIndex(index = child)
        while (child > 0 && compare(elements[child], elements[parent]) > 0) {
            Collections.swap(elements, child, parent)
            child = parent
            parent = parentIndex(index = child)
        }
    }

    private fun siftDown(index: Int) {
        var parent: Int = index
        while (true) {
            val left: Int = leftChildIndex(index = parent)
            val right: Int = rightChildIndex(index = parent)
            var candidate: Int = parent
            if (left < count && compare(elements[left], elements[candidate]) > 0) {
                candidate = left
            }
            if (right < count && compare(elements[right], elements[candidate]) > 0) {
                candidate = right
            }
            if (candidate == parent) {
                return
            }
            Collections.swap(elements, parent, candidate)
            parent = candidate
        }
    }

    /**
     * Searching for an element in a heap is, in the worst-case, an O(n) operation, since
     * every element in the array may be checked
     */
    private fun index(element: T, i: Int): Int? {
        // Return 'null' if the index is greater than the number of elements in the array
        if (i >= count) return null
        /**
         * Return 'null' if the current element has a higher priority than the current element
         * at index 'i'. If it does, the element searched cannot possibly be lower in the heap.
         */
        if (compare(element, elements[i]) > 0) return null
        // Return the current index if there's a match
        if (element == elements[i]) return i

        // Recursively search for the element starting from the left child of 'i'
        val leftChildIndex: Int? = index(element, leftChildIndex(index = i))
        if (leftChildIndex != null) return leftChildIndex
        // Recursively search for the element starting from the right child of 'i'
        val rightChildIndex: Int? = index(element, rightChildIndex(index = i))
        if (rightChildIndex != null) return rightChildIndex
        // Return 'null' if this point is reached, i.e. no search has been successful
        return null
    }

}

class ComparableHeapImplOne<T : Comparable<T>> : AbstractHeap<T>() {

    override fun compare(a: T, b: T): Int = a.compareTo(b)

    companion object {
        fun <T : Comparable<T>> create(
            elements: ArrayList<T>
        ): Heap<T> = ComparableHeapImplOne<T>().apply { heapify(values = elements) }
    }

}

class ComparableHeapImplTwo<T : Any>(
    private val comparator: Comparator<T>
) : AbstractHeap<T>() {

    override fun compare(a: T, b: T): Int = comparator.compare(a, b)

    companion object {
        fun <T : Comparable<T>> create(
            elements: ArrayList<T>,
            comparator: Comparator<T>
        ): Heap<T> = ComparableHeapImplTwo<T>(comparator = comparator).apply { heapify(values = elements) }
    }

}

/**
 * Write a function to find the nth smallest integer in an unsorted array
 */
fun getNthSmallestElement(n: Int, elements: ArrayList<Int>): Int? {
    if (n <= 0 || elements.isEmpty()) return null

    val heap = ComparableHeapImplOne.create(elements = arrayListOf<Int>())

    elements.forEach {  e ->
        val maxElement: Int? = heap.peek()
        if (heap.count < n) {
            heap.insert(element = e)
        } else if (maxElement != null && maxElement > e) {
            heap.remove()
            heap.insert(element = e)
        }
    }
    return heap.peek()
}

fun main() {
    // Maxn-heap example
    val array1: ArrayList<Int> = arrayListOf(1, 12, 3, 4, 1, 6, 8, 7)
    val priorityQueue = ComparableHeapImplOne.create(elements = array1)
    while (!priorityQueue.isEmpty) {
        println(priorityQueue.remove())
    }

    // Min-heap example
    val array2: ArrayList<Int> = arrayListOf(1, 12, 3, 4, 1, 6, 8, 7)
    val inverseComparator: Comparator<Int> = Comparator { o1, o2 -> o2.compareTo(o1) }
    val minHeap = ComparableHeapImplTwo.create(elements = array2, comparator = inverseComparator)
    while (!minHeap.isEmpty) {
        println(minHeap.remove())
    }
}
