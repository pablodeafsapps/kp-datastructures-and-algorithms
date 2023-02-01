package org.deafsapps.kpdatastructuresandalgorithms.utils

import org.deafsapps.kpdatastructuresandalgorithms.Trie

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
