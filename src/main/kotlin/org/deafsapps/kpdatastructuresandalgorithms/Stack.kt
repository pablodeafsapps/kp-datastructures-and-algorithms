package org.deafsapps.kpdatastructuresandalgorithms

/**
 * The stack data structure is identical, in concept, to a physical stack of objects. When you add an item to a stack,
 * you place it on top of the stack. When you remove an item from a stack, you always remove the top-most item.
 * There are only two essential operations for a stack:
 * - push : Adding an element to the top of the stack.
 * - pop : Removing the top element of the stack.
 */
interface Stack<T : Any> {
    val count: Int
    val isEmpty: Boolean
        get() = count == 0
    fun push(element: T) : Stack<T>
    fun pop(): T?
    fun peek(): T?
}

class StackImpl<T : Any> : Stack<T> {

    private val data: MutableList<T> = arrayListOf()

    override val count: Int
        get() = data.size

    override fun push(element: T) : Stack<T> = apply {
        data.add(element)
    }

    override fun pop(): T? =
        if (isEmpty) {
            null
        } else {
            data.removeAt(count - 1)
        }

    override fun peek(): T? = data.lastOrNull()

    override fun toString(): String = buildString {
        appendLine("----top----")
        data.asReversed().forEach {
            appendLine("$it")
        }
        appendLine("-----------")
    }

    companion object {
        fun <T : Any> create(items: Iterable<T>): Stack<T> =
            StackImpl<T>().apply {
                for (item in items) {
                    push(item)
                }
            }

        fun <T : Any> stackOf(vararg elements: T): Stack<T> =
            create(elements.asList())
    }

}

fun main() {

    val dummyIterable: Iterable<Int> = arrayListOf(2, 1, 0, -1)

    // Stack example
//    val stack: Stack<Int> = StackImpl.create(items = dummyIterable)
    val stack: Stack<Int> = StackImpl.stackOf(2, 1, 0, -1)
    stack.pop()

    println(stack)

}
