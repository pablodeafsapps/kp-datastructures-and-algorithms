package org.deafsapps.kpdatastructuresandalgorithms.data_structures.utils

import org.deafsapps.kpdatastructuresandalgorithms.data_structures.Trie

fun Trie<Char>.insert(string: String) {
    insert(string.toList())
}

fun Trie<Char>.contains(string: String): Boolean =
    contains(string.toList())

fun Trie<Char>.remove(string: String) {
    remove(string.toList())
}

fun Trie<Char>.collections(prefix: String): List<String> =
    collections(prefix.toList()).map { it.joinToString(separator = "") }
