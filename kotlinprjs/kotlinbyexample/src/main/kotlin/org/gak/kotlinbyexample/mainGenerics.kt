package org.gak.kotlinbyexample


// Generic class MutableStack<E> where E is called the generic type paramete
class MutableStack<E>(vararg items: E) {              // 1

    private val elements = items.toMutableList()

    fun push(element: E) = elements.add(element)        // 2

    fun peek(): E = elements.last()                     // 3

    fun pop(): E = elements.removeAt(elements.size - 1)

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    override fun toString() = "MutableStack(${elements.joinToString()})"
}

fun mainGenerics() {
    println("**** Generics ***")
    // At use, the type parameter is assigned to a specific type as
    // Int by declaring a MutableStack<Int> or MutableStack<Contact>
    val stackInt: MutableStack<Int> = MutableStack()
    println("Initial Stack ==> ${stackInt.toString()}")
    stackInt.push(1)
    stackInt.push(5,)
    println("Updated Stack ==> ${stackInt.toString()}")
}