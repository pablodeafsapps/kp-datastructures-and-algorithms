package org.deafsapps.kpdatastructuresandalgorithms

import kotlin.math.max
import kotlin.math.pow

typealias avlVisitor<T> = (T) -> Unit

/**
 * Binary search trees and AVL trees share much of the same implementation; in fact, all that it's required is adding a
 * balancing component.
 */
class AVLTree<T : Comparable<T>> {

    var root: Node<T>? = null

    override fun toString() = root?.toString() ?: "empty tree"

    fun insert(value: T) {
        root = insert(root, value)
    }

    fun remove(value: T) {
        root = remove(root, value)
    }

    fun contains(value: T): Boolean {
        var current = root

        while (current != null) {
            if (current.value == value) {
                return true
            }
            current = if (value < current.value) {
                current.leftChild
            } else {
                current.rightChild
            }
        }

        return false
    }

    private fun insert(node: Node<T>?, value: T): Node<T> {
        node ?: return Node(value)
        if (value < node.value) {
            node.leftChild = insert(node.leftChild, value)
        } else {
            node.rightChild = insert(node.rightChild, value)
        }
        /**
         * The following lines mark the difference between BinarySearchTree::insert and AVLTree::insert
         *
         * Instead of returning the node directly after inserting, it is passed into balanced().
         * This ensures every node in the call stack is checked for balancing issues.
         * Node's height is also updated
         */
        val balancedNode = balanced(node)
        balancedNode.height = max(balancedNode.leftHeight, balancedNode.rightHeight) + 1
        return balancedNode
    }

    private fun remove(node: Node<T>?, value: T): Node<T>? {
        node ?: return null

        when {
            value == node.value -> {
                if (node.leftChild == null && node.rightChild == null) {
                    return null
                }
                if (node.leftChild == null) {
                    return node.rightChild
                }
                if (node.rightChild == null) {
                    return node.leftChild
                }
                node.rightChild?.min?.value?.let { node.value = it }
                node.rightChild = remove(node.rightChild, node.value)
            }
            value < node.value -> node.leftChild = remove(node.leftChild, value)
            else -> node.rightChild = remove(node.rightChild, value)
        }
        /**
         * The following lines mark the difference between BinarySearchTree::remove and AVLTree::remove
         *
         * Instead of returning the node directly after removing, it is passed into balanced().
         * This ensures every node in the call stack is checked for balancing issues.
         * Node's height is also updated
         */
        val balancedNode = balanced(node = node)
        balancedNode.height = max(balancedNode.leftHeight, balancedNode.rightHeight) + 1
        return balancedNode
    }

    /**
     * This function does work if all children are right
     */
    private fun leftRotate(node: Node<T>): Node<T> {
        val pivot = node.rightChild!!
        node.rightChild = pivot.leftChild
        pivot.leftChild = node
        node.height = max(node.leftHeight, node.rightHeight) + 1
        pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
        return pivot
    }

    /**
     * This function does work if all children are left
     */
    private fun rightRotate(node: Node<T>): Node<T> {
        val pivot = node.leftChild!!
        node.leftChild = pivot.rightChild
        pivot.rightChild = node
        node.height = max(node.leftHeight, node.rightHeight) + 1
        pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
        return pivot
    }

    /**
     * This function is called so that all children get turned into right ones
     */
    private fun rightLeftRotate(node: Node<T>): Node<T> {
        val rightChild = node.rightChild ?: return node
        node.rightChild = rightRotate(node = rightChild)
        return leftRotate(node = node)
    }

    /**
     * This function is called so that all children get turned into left ones
     */
    private fun leftRightRotate(node: Node<T>): Node<T> {
        val leftChild = node.leftChild ?: return node
        node.leftChild = leftRotate(node = leftChild)
        return rightRotate(node = node)
    }

    private fun balanced(node: Node<T>): Node<T> =
        when (node.balanceFactor) {
            // A 'balanceFactor' of 2 suggests that the left child contains more nodes than the right child
            2 -> {
                if (node.leftChild?.balanceFactor == -1) {
                    leftRightRotate(node = node)
                } else {
                    rightRotate(node = node)
                }
            }
            // A 'balanceFactor' of -2 suggests that the right child contains more nodes than the left child
            -2 -> {
                if (node.rightChild?.balanceFactor == 1) {
                    rightLeftRotate(node = node)
                } else {
                    leftRotate(node = node)
                }
            }
            else -> node
        }

    class Node<T>(var value: T) {

        var leftChild: Node<T>? = null
        var rightChild: Node<T>? = null

        /**
         * Balance Factor: In a balanced tree, the height of the left and right children of each node must differ at
         * most by 1
         */
        var height: Int = 0
        val leftHeight: Int
            get() = leftChild?.height ?: -1
        val rightHeight: Int
            get() = rightChild?.height ?: -1
        val balanceFactor: Int
            get() = leftHeight - rightHeight

        val min: Node<T>
            get() = leftChild?.min ?: this

        override fun toString() = diagram(this)

        fun traverseInOrder(visit: avlVisitor<T>) {
            leftChild?.traverseInOrder(visit)
            visit(value)
            rightChild?.traverseInOrder(visit)
        }

        fun traversePreOrder(visit: avlVisitor<T>) {
            visit(value)
            leftChild?.traversePreOrder(visit)
            rightChild?.traversePreOrder(visit)
        }

        fun traversePostOrder(visit: avlVisitor<T>) {
            leftChild?.traversePostOrder(visit)
            rightChild?.traversePostOrder(visit)
            visit(value)
        }

        /**
         * Calculate the number of leaf nodes from the current node height
         */
        fun getLeafNodes(height: Int = this.height): Int =
            2.0.pow(height).toInt()

        private fun diagram(node: Node<T>?, top: String = "", root: String = "", bottom: String = ""): String =
            node?.let {
                if (node.leftChild == null && node.rightChild == null) {
                    "$root${node.value}\n"
                } else {
                    diagram(node.rightChild, "$top ", "$top┌──", "$top│ ") +
                            root + "${node.value}\n" +
                            diagram(node.leftChild, "$bottom│ ", "$bottom└──", "$bottom ")
                }
            } ?: "${root}null\n"

    }

}

fun main() {

    val tree: AVLTree<Int> = AVLTree()

    (0..14).forEach { value -> tree.insert(value = value) }

    with(tree) {
        insert(value = 15)
        insert(value = 10)
        insert(value = 16)
        insert(value = 18)
    }
    println(tree)
    tree.remove(10)
    println(tree)

}
