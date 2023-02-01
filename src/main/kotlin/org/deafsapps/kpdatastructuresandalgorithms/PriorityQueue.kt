package org.deafsapps.kpdatastructuresandalgorithms

import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

/**
 * A priority queue is another version of a queue, which, instead of using
 * FIFO ordering, elements are dequeued in priority order. A priority queue
 * is especially useful when you need to identify the maximum or minimum value
 * within a list of elements.
 *
 * A priority queue can have either a:
 * - Max-priority: The element at the front is always the largest.
 * - Min-priority: The element at the front is always the smallest.
 *
 * Some useful applications of a priority queue include:
 * - Dijkstra’s algorithm: Uses a priority queue to calculate the minimum cost.
 * - A* pathfinding algorithm: Uses a priority queue to track the unexplored routes
 * that will produce the path with the shortest length.
 * - Heap sort: Many heap sorts use a priority queue.
 * - Huffman coding: Useful for building a compression tree. A min-priority queue is
 * used to repeatedly find two nodes with the smallest frequency that don't yet have
 * a parent node.
 */
abstract class AbstractPriorityQueue<T : Any> : Queue<T> {

    // Among other options, using a 'Heap' is a natural choice for a priority queue
    abstract val heap: Heap<T>
    // 'count' uses the same property of the heap
    override val count: Int
        get() = heap.count

    /**
     * Calling 'enqueue()' adds the element into the heap using insert(), which
     * guarantees to arrange data internally so that the one with the highest priority is
     * ready to extract. The overall complexity of 'enqueue()' is the same as 'insert()':
     * O(log n).
     */
    override fun enqueue(element: T): Boolean {
        heap.insert(element = element)
        return true
    }

    /**
     * Calling 'dequeue()' removes the root element from the heap using
     * 'remove()'. The Heap guarantees to get the one with the highest
     * priority. The overall complexity of 'dequeue()' is the same as
     * 'remove()': O(log n).
     */
    override fun dequeue(): T? = heap.remove()

    // 'peek()' delegates to the same method of the heap
    override fun peek(): T? = heap.peek()

}

abstract class AbstractPriorityQueueArrayList<T : Any> : Queue<T> {

    override val count: Int
        get() = elements.size
    protected val elements: ArrayList<T> = ArrayList()

    abstract fun sort()

    override fun peek(): T? = elements.firstOrNull()

    override fun dequeue(): T? =
        if (isEmpty) null else elements.removeAt(0)

    override fun enqueue(element: T): Boolean {
        elements.add(element = element)
        sort()
        return true
    }

    override fun toString(): String = elements.toString()

}

class ComparablePriorityQueueImpl<T : Comparable<T>> : AbstractPriorityQueue<T>() {

    override val heap: Heap<T> = ComparableHeapImpl()

}

class ComparablePriorityQueueArrayList<T : Comparable<T>> : AbstractPriorityQueueArrayList<T>() {

    override fun sort() {
        elements.sort()
    }

}

class ComparatorPriorityQueueImpl<T : Any>(
    comparator: Comparator<T>
) : AbstractPriorityQueue<T>() {

    override val heap: Heap<T> = ComparatorHeapImpl(comparator = comparator)

}

fun main() {

    val priorityQueue1: AbstractPriorityQueue<Int> = ComparablePriorityQueueImpl()
    arrayListOf(1, 12, 3, 4, 1, 6, 8, 7).forEach { priorityQueue1.enqueue(it) }
    while (!priorityQueue1.isEmpty) {
        println(priorityQueue1.dequeue())
    }

    val stringLengthComparator: Comparator<String> = Comparator { o1, o2 ->
        val length1: Int = o1?.length ?: -1
        val length2: Int = o2?.length ?: -1
        length1 - length2
    }
    val priorityQueue2: AbstractPriorityQueue<String>  = ComparatorPriorityQueueImpl(comparator = stringLengthComparator)
    arrayListOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").forEach {
        priorityQueue2.enqueue(element = it)
    }
    while (!priorityQueue2.isEmpty) {
        println(priorityQueue2.dequeue())
    }

}
