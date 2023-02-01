package org.deafsapps.kpdatastructuresandalgorithms.data_structures

import org.deafsapps.kpdatastructuresandalgorithms.data_structures.utils.DoublyLinkedList
import org.deafsapps.kpdatastructuresandalgorithms.data_structures.utils.RingBuffer

/**
 * Queues use FIFO or first in, first out ordering, meaning the first element that was added will always be the first
 * one removed. Queues are handy when you need to maintain the order of your elements to process later.
 */
interface Queue<T : Any> {
    val count: Int
    val isEmpty: Boolean
        get() = count == 0
    fun enqueue(element: T): Boolean
    fun dequeue(): T?
    fun peek(): T?
}

class ArrayListQueue<T : Any> : Queue<T> {

    override val count: Int
        get() = data.size
    private val data: MutableList<T> = arrayListOf()

    override fun dequeue(): T? = if (isEmpty) null else data.removeAt(0)

    override fun peek(): T? = data.firstOrNull()

    override fun enqueue(element: T): Boolean = data.add(element)

    override fun toString(): String = data.toString()

    companion object {
        fun <T : Any> create(items: Iterable<T>): Queue<T> =
            ArrayListQueue<T>().apply {
                for (item in items) {
                    enqueue(item)
                }
            }

        fun <T : Any> queueOf(vararg elements: T): Queue<T> =
            create(elements.asList())
    }

}

class LinkedListQueue<T : Any> : Queue<T> {

    override val count: Int
        get() = size
    private var size = 0
    private val data = DoublyLinkedList<T>()

    override fun dequeue(): T? {
        val firstNode: DoublyLinkedList.Node<T> = data.first ?: return null
        size--
        return data.remove(firstNode)
    }

    override fun peek(): T? = data.first?.value

    override fun enqueue(element: T): Boolean {
        data.append(element)
        size++
        return true
    }

    override fun toString(): String = data.toString()

    companion object {
        fun <T : Any> create(items: Iterable<T>): Queue<T> =
            LinkedListQueue<T>().apply {
                for (item in items) {
                    enqueue(item)
                }
            }

        fun <T : Any> queueOf(vararg elements: T): Queue<T> =
            create(elements.asList())
    }

}

class RingBufferQueue<T : Any>(size: Int) : Queue<T> {

    private val data: RingBuffer<T> = RingBuffer(size = size)
    override val count: Int
        get() = data.count

    override fun dequeue(): T? = data.read()

    override fun peek(): T? = data.first

    override fun enqueue(element: T): Boolean = data.write(element = element)

    override fun toString(): String = data.toString()

    companion object {
        fun <T : Any> create(size: Int, items: Iterable<T>? = null): Queue<T> =
            RingBufferQueue<T>(size = size).apply {
                if (items != null) {
                    for (item in items) {
                        enqueue(item)
                    }
                }
            }

        fun <T : Any> queueOf(vararg elements: T): Queue<T> =
            create(size = elements.size, items = elements.asList())

        fun <T : Any> emptyQueueOf(size: Int): Queue<T> =
            create(size = size)
    }

}

class StackQueue<T : Any> : Queue<T> {

    private val enqueueStack: Stack<T> = StackImpl()
    private val dequeueStack: Stack<T> = StackImpl()

    override val isEmpty: Boolean
        get() = enqueueStack.isEmpty && dequeueStack.isEmpty

    override val count: Int
        get() = enqueueStack.count + dequeueStack.count

    override fun dequeue(): T? {
        if (dequeueStack.isEmpty) {
            transferElements()
        }
        return dequeueStack.pop()
    }

    override fun peek(): T? {
        if (dequeueStack.isEmpty) {
            transferElements()
        }
        return dequeueStack.peek()
    }

    override fun enqueue(element: T): Boolean {
        enqueueStack.push(element = element)
        return true
    }

    override fun toString(): String = "enqueue:\n$enqueueStack\ndequeue:\n$dequeueStack\n"

    /**
     * Move elements from the 'enqueue' stack to the 'dequeue' stack. This function should be called when the 'dequeue',
     * meaning there's not available to pop from the 'Queue'.
     */
    private fun transferElements() {
        var nextElement = enqueueStack.pop()
        while (nextElement != null) {
            dequeueStack.push(element = nextElement)
            nextElement = enqueueStack.pop()
        }
    }

    companion object {
        fun <T : Any> create(items: Iterable<T>): Queue<T> =
            StackQueue<T>().apply {
                for (item in items) {
                    enqueue(item)
                }
            }

        fun <T : Any> queueOf(vararg elements: T): Queue<T> =
            create(elements.asList())
    }

}

fun main() {

    val dummyIntIterable: Iterable<Int> = arrayListOf(2, 1, 0, -1)
    val dummyStringIterable: Iterable<String> = arrayListOf("Ray", "Brian", "Eric")

    // ArrayListQueue example
    println("\n--- 'ArrayListQueue' example ---")
//    val arrayListQueue: Queue<Int> = ArrayListQueue.create(items = dummyIntIterable)
    val arrayListQueue: Queue<Int> = ArrayListQueue.queueOf(2, 1, 0, -1)
    arrayListQueue.dequeue()

    println(arrayListQueue)

    // LinkedListQueue example
    println("\n--- 'LinkedListQueue' example ---")
//    val linkedListQueue: Queue<Int> = LinkedListQueue.create(items = dummyStringIterable)
    val linkedListQueue: Queue<String> = LinkedListQueue.queueOf("Ray", "Brian", "Eric")
    linkedListQueue.dequeue()

    println(linkedListQueue)

    // RingBufferQueue example
    println("\n--- 'RingBufferQueue' example ---")
//    val ringBufferQueue: Queue<String> = RingBufferQueue.create(size = dummyStringIterable.count(), items = dummyStringIterable).apply {
    val ringBufferQueue = RingBufferQueue.emptyQueueOf<String>(10).apply {
        enqueue("Ray")
        enqueue("Brian")
        enqueue("Eric")
    }

    println(ringBufferQueue)
    ringBufferQueue.dequeue()
    println(ringBufferQueue)
    println("Next up: ${ringBufferQueue.peek()}")

    // StackQueue example
    println("\n--- 'StackQueue' example ---")
//    val stackQueue: Queue<String> = StackQueue.create(items = dummyStringIterable).apply {
    val stackQueue = StackQueue.queueOf<String>("Ray", "Brian", "Eric")
    println(stackQueue)
    stackQueue.dequeue()   // since 'dequeue' is empty, elements will be transferred
    println(stackQueue)
    println("Next up: ${stackQueue.peek()}")

}
