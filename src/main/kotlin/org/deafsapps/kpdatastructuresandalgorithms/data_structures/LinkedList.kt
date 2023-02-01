package org.deafsapps.kpdatastructuresandalgorithms.data_structures

/**
 * A linked list is a collection of values arranged in a linear, unidirectional sequence. A linked list has several
 * advantages over contiguous storage options such as the Kotlin Array or ArrayList:
 * - Constant time insertion and removal from the front of the list.
 * - Reliable performance characteristics.
 */
class LinkedList<T : Any> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    override fun toString(): String =
        if (isEmpty()) {
            "Empty list"
        } else {
            head.toString()
        }

    fun push(value: T): LinkedList<T> = apply {
        head = Node(value = value, next = head)
        // In case of an empty list, 'tail' needs to be initialised
        if (tail == null) {
            tail = head
        }
        size++
    }

    fun append(value: T): LinkedList<T> = apply {
        if (isEmpty()) {
            push(value)
            return@apply
        }
        val newNode = Node(value = value)
        // The old 'tail' points to 'newNode'
        tail?.next = newNode
        // the new 'tail' is actually 'newNode'
        tail = newNode
        size++
    }

    fun insertNodeWithValueAndIndex(index: Int, value: T): LinkedList<T> = apply {
        val afterNode: Node<T>? = nodeAt(index = index)
        if (afterNode != null) {
            insert(value = value, afterNode = afterNode)
        } else {
            println("Index out of bound, insertion not performed.")
        }
    }

    fun pop(): LinkedList<T> = apply {
        if (isEmpty()) return@apply
        head = head?.next
        size--
        if (isEmpty()) {
            tail = head
        }
    }

    fun removeLast(): LinkedList<T> = apply {
        if (isEmpty()) return@apply
        if (size == 1) {
            pop()
            return@apply
        }
        val newLastNode: Node<T>? = nodeAt(size - 2)
        newLastNode?.next = null
        tail = newLastNode
        size--
    }

    fun removeNodeWithIndex(index: Int): LinkedList<T> = apply {
        if (isEmpty()) return@apply
        val prevIndex = index - 1
        when {
            prevIndex < 0 -> pop()
            else -> {
                val prevRemovableNode: Node<T>? = nodeAt(index = prevIndex)
                if (prevRemovableNode != null) {
                    remove(prevRemovableNode = prevRemovableNode)
                }
            }
        }
    }

    private fun nodeAt(index: Int): Node<T>? {
        var currentNode: Node<T>? = head
        var currentIndex = 0
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    private fun insert(value: T, afterNode: Node<T>) {
        if (tail == afterNode) {
            append(value)
            return
        }
        val newNode: Node<T> = Node(value = value, next = afterNode.next)
        afterNode.next = newNode
        size++
    }

    private fun remove(prevRemovableNode: Node<T>) {
        prevRemovableNode.next = prevRemovableNode.next?.next
        size--
    }

    private fun isEmpty(): Boolean = size == 0

    data class Node<T : Any>(var value: T, var next: Node<T>? = null) {
        override fun toString(): String =
            if (next != null) {
                "$value -> ${next.toString()}"
            } else {
                "$value"
            }
    }

}

fun main() {

    // Nodes example
    val node1: LinkedList.Node<Int> = LinkedList.Node(value = 1)
    val node2: LinkedList.Node<Int> = LinkedList.Node(value = 2)
    val node3: LinkedList.Node<Int> = LinkedList.Node(value = 3)

    node1.next = node2
    node2.next = node3

    println(node1)

    // LinkedList example
    val linkedList: LinkedList<Int> = LinkedList()
    linkedList
        .push(2)
        .push(1)
        .append(9)
        .append(0)
        .append(-1)
        .insertNodeWithValueAndIndex(3, 7)
        .pop()
        .pop()
        .removeLast()
        .removeNodeWithIndex(5)
        .removeNodeWithIndex(0)

    println(linkedList)

}
