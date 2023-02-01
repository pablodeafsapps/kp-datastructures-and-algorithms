package org.deafsapps.kpdatastructuresandalgorithms.data_structures

import org.deafsapps.kpdatastructuresandalgorithms.data_structures.utils.collections
import org.deafsapps.kpdatastructuresandalgorithms.data_structures.utils.contains
import org.deafsapps.kpdatastructuresandalgorithms.data_structures.utils.insert
import org.deafsapps.kpdatastructuresandalgorithms.data_structures.utils.remove

/**
 * The trie (pronounced try) is a tree that specializes in storing data that can be
 * represented as a collection.
 */
class Trie<Key: Any> {

    private val root: Node<Key> = Node(key = null, parent = null)

    /**
     * Tries work with lists of the Key type. The trie takes the list and represents it as a
     * series of nodes in which each node maps to an element in the list
     */
    fun insert(list: List<Key>) {
        // 'current' keeps track of the traversal progress, starting with the 'root' node
        var current: Node<Key> = root
        list.forEach { element ->
            val child = current.children[element] ?: Node(key = element, parent = current)
            current.children[element] = child
            current = child
        }
        current.isTerminating = true
    }

    fun contains(list: List<Key>): Boolean {
        var current: Node<Key> = root
        list.forEach { element ->
            val child = current.children[element] ?: return false
            current = child
        }
        return current.isTerminating
    }

    fun remove(list: List<Key>) {
        var current: Node<Key> = root
        list.forEach { element ->
            val child = current.children[element] ?: return
            current = child
        }
        // If 'current' is not a 'leaf' node, the list is longer than the candidate (no match)
        if (!current.isTerminating) return

        current.isTerminating = false

        val parent = current.parent
        while (parent != null && current.children.isEmpty() && !current.isTerminating) {
            parent.children.remove(current.key)
            current = parent
        }

    }

    fun collections(prefix: List<Key>): List<List<Key>> {
        var current: Node<Key> = root
        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }
        return collections(prefix, current)
    }

    private fun collections(prefix: List<Key>, node: Node<Key>?): List<List<Key>> {
        val results: MutableList<List<Key>> = mutableListOf()
        if (node?.isTerminating == true) {
            results.add(prefix)
        }
        node?.children?.forEach{ (key, node) ->
            results.addAll(collections(prefix + key, node))
        }
        return results
    }

    /**
     * Note that:
     * - key holds the data for the node. This is optional because the root node of the trie
     * has no key.
     * - A Trie node holds a reference to its parent. This simplifies removing items.
     * - In binary search trees, nodes have a left and right child. In a trie, a node needs to
     * hold multiple different elements. A children map will help on that.
     * - 'isTerminating' acts as an indicator for the end of a collection.
     */
    class Node<Key : Any>(var key: Key?, var parent: Node<Key>?) {

        val children: HashMap<Key, Node<Key>> = HashMap()
        var isTerminating: Boolean = false

    }

}

fun main() {

    // Basic example
    val trie: Trie<Char> = Trie<Char>().apply {
        insert("cut")
        insert("cute")
    }

    println("\n*** Before removing ***")
    assert(trie.contains("cut"))
    println("'cut' is in the trie")
    assert(trie.contains("cute"))
    println("'cute' is in the trie")

    println("\n*** After removing cut ***")
    trie.remove("cut")
    assert(!trie.contains("cut"))
    assert(!trie.contains("cute"))
    println("'cut' is still in the trie")

    // Prefix matching
    val pmTrie: Trie<Char> = Trie<Char>().apply {
        insert("car")
        insert("card")
        insert("care")
        insert("cared")
        insert("cars")
        insert("carbs")
        insert("carapace")
        insert("cargo")
    }

    println("\nCollections starting with 'car'")
    val prefixedWithCar = pmTrie.collections("car")
    println(prefixedWithCar)

    println("\nCollections starting with 'care'")
    val prefixedWithCare = pmTrie.collections("care")
    println(prefixedWithCare)

}
