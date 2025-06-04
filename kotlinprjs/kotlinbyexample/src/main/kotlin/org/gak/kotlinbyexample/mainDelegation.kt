package org.gak.kotlinbyexample

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

// Kotlin program to illustrate the
// concept of delegation

interface delegation
{
    fun mymessage()
    fun mymessageline()
}

class delegationimplementation(val y: String) : delegation
{
    override fun mymessage()
    {
        print(y)
    }
    override fun mymessageline()
    {
        println(y)
    }
}

class Newfeature(m: delegation) : delegation by m
{
    override fun mymessage()
    {
        print("GeeksforGeeks")
    }
}



// Kotlin program to illustrate the
// concept of delegation
interface delegation1
{
    val value: String
    fun mymessage()
}

class delegationimplementation1(val y: String) : delegation1
{
    override val value = "delegationimplementation y = $y"
    override fun mymessage()
    {
        println(value)
    }
}

class Newfeatures1(a: delegation1) : delegation1 by a
{
    override val value = "GeeksforGeeks"
}

fun mainDelegation11()
{
    val b = delegationimplementation1("Hello!GFG")
    val derived = Newfeatures1(b)

    derived.mymessage()
    println(derived.value)
}

// Delegation means passing the responsibility to another class or method.
// Kotlin supports delegation design pattern by introducing a new keyword by.
//   Using this keyword or delegation methodology, Kotlin allows the derived
//   class to access all the implemented public methods of an interface
//   through a specific object.

interface Base {
    fun printMe() //abstract method
}
class BaseImpl(val x: Int) : Base {
    override fun printMe() { println(x) }   //implementation of the method
}
class Derived(b: Base) : Base by b  // delegating the public method on the object b

fun mainDelegation22() {
    val b = BaseImpl(10)
    Derived(b).printMe() // prints 10 :: accessing the printMe() method
}


class UserDelegation {
    var name: String by Delegates.observable("Welcome to Tutorialspoint.com") {
            prop, old, new ->
        println("$old -> $new")
    }

    override fun toString(): String {
        return "UserDelegation(name=${this.name})"
    }
}


class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name} in $thisRef.'")
    }
}

class Example {
    var p: String by Delegate()
}

class Car {
    var name: String by Delegates.observable("Initial value") {
        prop, old, new ->
        println("$old -> $new")
    }
}

// Storing properties in a map
class Data(val map: Map<String, Any?>){
    val name: String by map
    val assetid: Int by map
}

interface SoundBehavior {                                                          // 1
    fun makeSound()
}

class ScreamBehavior(val n: String): SoundBehavior {                                // 2
    override fun makeSound() = println("${n.uppercase()} !!!")
}

class RockAndRollBehavior(val n: String): SoundBehavior {                           // 2
    override fun makeSound() = println("I'm The King of Rock 'N' Roll: $n")
}

// Tom Araya is the "singer" of Slayer
class TomAraya(n: String): SoundBehavior by ScreamBehavior(n)                       // 3

// You should know ;)
class ElvisPresley(n: String): SoundBehavior by RockAndRollBehavior(n)              // 3

fun mainDelegationKotlin() {
    val tomAraya = TomAraya("Thrash Metal")
    tomAraya.makeSound()                                                           // 4
    val elvisPresley = ElvisPresley("Dancin' to the Jailhouse Rock.")
    elvisPresley.makeSound()
}

// Main function
fun mainDelegation()
{
    val data = Data(mapOf("name" to "Kotlin", "assetid" to 25))
    println(data.name)
    println(data.assetid)

    val b = delegationimplementation("\nWelcome, GFG!")

    Newfeature(b).mymessage()
    Newfeature(b).mymessageline()

    mainDelegation11()
    mainDelegation22()

// Lazy is a lambda function which takes a property as an input and in
//   return gives an instance of Lazy<T>, where <T> is basically the type
//   of the properties it is using
// Lazy is a delegation of properties using some standard methods mentioned
//   in Kotlin library.

    val myVar: String by lazy {
        "Hello"
    }

    println("$myVar My dear friend")

// Delegation by onservables: Delegetion.Observable()
    val user = UserDelegation()
    user.name = "first"
    user.name = "second"
    println(user)

    var eg = Example()
    println(eg.p)
    eg.p = "abcd"
    println(eg.p)

    var car = Car()
    car.name = "First Message"
    car.name = "Second Message"

    mainDelegationKotlin()

    println("*".repeat(20))
    val a: MutableStateFlow<Int> = MutableStateFlow(0)
    a.value = 2
    println(a.value)
    a.update {
        v -> (v*3)/2
    }
    println(a.value)
    println(a.compareAndSet(3,4))
    println(a.getAndUpdate { 5 })
    println()
    println(a.updateAndGet { 10 })

}
