package org.deafsapps.kpdatastructuresandalgorithms.data_structures

import kotlin.math.max

typealias btVisitor<T> = (T?) -> Unit

class BinaryTree {

    class Node<T>(var value: T) {
        var leftChild: Node<T>? = null
        var rightChild: Node<T>? = null
        // Gets the minimum-value 'Node' recursively, which is that 'Node' whose 'leftChild' property is 'null'
        val min: Node<T>
            get() = leftChild?.min ?: this

        override fun toString(): String = diagram(this)

        /**
         * Override equals() to check whether two binary search trees are equal.
         */
        override fun equals(other: Any?): Boolean =
            if (other != null && other is Node<*>) {
                this.value == other.value &&
                        this.leftChild == other.leftChild &&
                        this.rightChild == other.rightChild
            } else {
                false
            }

        fun traverseInOrder(visit: btVisitor<T>) {
            leftChild?.traverseInOrder(visit = visit)
            visit(value)
            rightChild?.traverseInOrder(visit = visit)
        }

        fun traversePreOrder(visit: btVisitor<T>) {
            visit(value)
            leftChild?.traversePreOrder(visit = visit)
            rightChild?.traversePreOrder(visit = visit)
        }

        fun traversePreOrderWithNull(visit: btVisitor<T>) {
            visit(value)
            leftChild?.traversePreOrder(visit = visit) ?: visit(null)
            rightChild?.traversePreOrder(visit = visit) ?: visit(null)
        }

        fun traversePostOrder(visit: btVisitor<T>) {
            leftChild?.traversePostOrder(visit = visit)
            rightChild?.traversePostOrder(visit = visit)
            visit(value)
        }

        fun height(node: Node<T>? = this, acc: Int = 0): Int =
            node?.let {  n ->
                max(height(node = n.leftChild, acc = acc.inc()), height(node = n.rightChild, acc = acc.inc()))
            } ?: acc

        fun serialize(node: Node<T> = this): MutableList<T?> {
            val list: MutableList<T?> = mutableListOf()
            node.traversePreOrderWithNull { list.add(it) }
            return list
        }

        fun deserialize(list: MutableList<T?>): Node<T?>? {
            if (list.size == 0) return null
            val rootValue = list.removeAt(list.size - 1)
            val root = Node<T?>(rootValue)
            root.leftChild = deserialize(list)
            root.rightChild = deserialize(list)

            return root
        }

        fun deserializeOptimized(list: MutableList<T?>): Node<T?>? =
            deserialize(list.asReversed())

        private fun diagram(
            node: Node<T>?, top: String = "", root: String = "", bottom: String = ""
        ): String = node?.let { n ->
            if (n.leftChild == null && n.rightChild == null) {
                "$root${node.value}\n"
            } else {
                diagram(n.rightChild, top = "$top ", root = "$top┌──", bottom = "$top│ ") +
                        root + "${n.value}\n" +
                        diagram(n.leftChild, top = "$bottom│ ", root = "$bottom└──", bottom = "$bottom ")
            }
        } ?: "${root}null\n"
    }

}

fun main() {
    val zero: BinaryTree.Node<Int> = BinaryTree.Node(0)
    val one: BinaryTree.Node<Int> = BinaryTree.Node(1)
    val five: BinaryTree.Node<Int> = BinaryTree.Node(5)
    val seven: BinaryTree.Node<Int> = BinaryTree.Node(7)
    val eight: BinaryTree.Node<Int> = BinaryTree.Node(8)
    val nine: BinaryTree.Node<Int> = BinaryTree.Node(9)
    seven.leftChild = one
    seven.rightChild = nine
    one.leftChild = zero
    one.rightChild = five
    nine.leftChild = eight

    val tree: BinaryTree.Node<Int> = seven

    println(tree)

    println("--- Traverse in-order ---")

    tree.traverseInOrder { println(it) }

    println("\n--- Traverse pre-order ---")

    tree.traversePreOrder { println(it) }

    println("\n--- Traverse post-order ---")

    tree.traversePostOrder { println(it) }

    println("\n--- Tree height ---")
    println("Tree height = ${tree.height()}")

    println("\n--- Tree serialization/deserialization ---")
    println("Tree:\n$tree")
    val serializedTree: MutableList<Int?> = tree.serialize()
    println("Tree serialized:\n${serializedTree}")
    println("Tree deserialized:\n${tree.deserialize(serializedTree)}")
//    println("Tree deserialized:\n${tree.deserializeOptimized(serializedTree)}")

}
