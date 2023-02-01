package org.deafsapps.kpdatastructuresandalgorithms.data_structures

/**
 * A binary search tree imposes two rules on the binary tree:
 * - The value of a left child must be less than the value of its parent.
 * - Consequently, the value of a right child must be greater than or equal to the value of its parent.
 *
 * As a result, lookup, insert, and removal have an average time complexity of O(log n)
 */
class BinarySearchTree<T : Comparable<T>> {

    var root: BinaryTree.Node<T>? = null

    override fun toString() = root?.toString() ?: "empty tree"

    fun insert(value: T) {
        root = insert(node = root, value = value)
    }

    /*
    fun contains(value: T): Boolean {
        root ?: return false
        var found: Boolean = false
        root?.traverseInOrder {
            if (value == it) {
                found = true
            }
        }
        return found
    }
    */

    fun contains(value: T): Boolean {
        var current = root

        while (current != null) {
            if (current.value == value) return true

            current = if (current.value > value) {
                current.rightChild
            } else {
                current.leftChild
            }
        }
        return false
    }

    fun remove(value: T) {
        root = remove(node = root, value = value)
    }

    /**
     * Create a function that checks if a binary tree is a binary search tree
     */
    fun isBinarySearchTree(node: BinaryTree.Node<T>? = root): Boolean {
        node ?: return true
        return if (
            (node.leftChild == null || node.leftChild?.value?.takeIf { it < node.value } != null) &&
            (node.rightChild == null || node.rightChild?.value?.takeIf { it > node.value } != null)
            ) {
            isBinarySearchTree(node.leftChild) && isBinarySearchTree(node.rightChild)
        } else {
            false
        }
    }

    /**
     * Create a method that checks if the current tree contains all the elements of another tree.
     */
    fun contains(subtree: BinarySearchTree<T>): Boolean {
        val set = mutableSetOf<T>().apply {
            root?.traverseInOrder { if (it != null) add(it) }
        }
        var isEqual = true
        subtree.root?.traverseInOrder { isEqual = isEqual && set.contains(it) }
        return isEqual
    }

    private fun insert(node: BinaryTree.Node<T>?, value: T): BinaryTree.Node<T> {
        node ?: return BinaryTree.Node(value = value)

        if (value < node.value) {
            node.leftChild = insert(node = node.leftChild, value = value)
        } else {
            node.rightChild = insert(node = node.rightChild, value = value)
        }

        return node
    }

    private fun remove(
        node: BinaryTree.Node<T>?,
        value: T
    ): BinaryTree.Node<T>? {
        node ?: return null

        when {
            value == node.value -> {
                // This first case relates to a no-children node
                if (node.leftChild == null && node.rightChild == null) {
                    return null
                }
                // The next two cases relate to nodes with only 1 child - easy reconnection
                if (node.leftChild == null) {
                    return node.rightChild
                }
                if (node.rightChild == null) {
                    return node.leftChild
                }
                node.rightChild?.min?.value?.let {
                    node.value = it
                }
                node.rightChild = remove(node.rightChild, node.value)
            }
            value < node.value -> node.leftChild = remove(node.leftChild, value)
            else -> node.rightChild = remove(node.rightChild, value)
        }
        return node
    }

}

fun main() {

    val bst: BinarySearchTree<Int> = BinarySearchTree<Int>().apply { root = BinaryTree.Node(value = 50) }

    with(bst) {
        insert(value = 30)
        insert(value = 60)
        insert(value = 40)
        insert(value = 35)
        insert(value = 45)
    }

    println(bst)

    with(bst) {
        remove(value = 40)
    }

    println(bst)

    println(bst.isBinarySearchTree())

}
