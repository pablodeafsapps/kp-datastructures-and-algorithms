package org.deafsapps.kpdatastructuresandalgorithms

typealias tVisitor<T> = (Tree.Node<T>) -> Unit

class Tree {

    class Node<T>(val value: T) {

        private val children: MutableList<Node<T>> = mutableListOf()

        fun add(child: Node<T>) = children.add(child)

        fun forEachDepthFirst(visit: tVisitor<T>) {
            visit(this)
            children.forEach { it.forEachDepthFirst(visit) }
        }

        fun forEachLevelOrder(visit: tVisitor<T>) {
            visit(this)
            val queue: Queue<Node<T>> = ArrayListQueue()
            children.forEach { queue.enqueue(it) }
            var node: Node<T>? = queue.dequeue()
            while (node != null) {
                visit(node)
                node.children.forEach { queue.enqueue(it) }
                node = queue.dequeue()
            }
        }

        fun search(value: T): Node<T>? {
            var result: Node<T>? = null

            forEachLevelOrder {
                if (it.value == value) {
                    result = it
                }
            }
            // The latest found value wins
            return result
        }

        fun printEachLevel() {
            val queue = ArrayListQueue<Node<T>>()
            var nodesLeftInCurrentLevel = 0
            queue.enqueue(this)
            while (queue.isEmpty.not()) {
                nodesLeftInCurrentLevel = queue.count
                while (nodesLeftInCurrentLevel > 0) {
                    val node = queue.dequeue()
                    node?.let {
                        print("${node.value} ")
                        node.children.forEach { queue.enqueue(it) }
                        nodesLeftInCurrentLevel--
                    } ?: break
                }
            }
        }

    }

}

fun main() {
    val tree: Tree.Node<String> = makeBeverageTree()
//    tree.forEachDepthFirst { println(it.value) }
//    tree.forEachLevelOrder { println(it.value) }
    tree.printEachLevel()
}

private fun makeBeverageTree(): Tree.Node<String> {
    val tree: Tree.Node<String> = Tree.Node("Beverages")
    val hot: Tree.Node<String> = Tree.Node("hot")
    val cold: Tree.Node<String> = Tree.Node("cold")
    val tea: Tree.Node<String> = Tree.Node("tea")
    val coffee: Tree.Node<String> = Tree.Node("coffee")
    val chocolate: Tree.Node<String> = Tree.Node("cocoa")
    val blackTea: Tree.Node<String> = Tree.Node("black")
    val greenTea: Tree.Node<String> = Tree.Node("green")
    val chaiTea: Tree.Node<String> = Tree.Node("chai")
    val soda: Tree.Node<String> = Tree.Node("soda")
    val milk: Tree.Node<String> = Tree.Node("milk")
    val gingerAle: Tree.Node<String> = Tree.Node("ginger ale")
    val bitterLemon: Tree.Node<String> = Tree.Node("bitter lemon")
    tree.add(hot)
    tree.add(cold)
    hot.add(tea)
    hot.add(coffee)
    hot.add(chocolate)
    cold.add(soda)
    cold.add(milk)
    tea.add(blackTea)
    tea.add(greenTea)
    tea.add(chaiTea)
    soda.add(gingerAle)
    soda.add(bitterLemon)

    return tree
}
