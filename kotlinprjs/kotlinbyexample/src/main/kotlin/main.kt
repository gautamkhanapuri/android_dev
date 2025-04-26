import org.gak.kotlinbyexample.*

fun main(args: Array<String>) {
    commandLineArgs(args)
    if (args.isNotEmpty()) {
        readInputAndPrint()
    }
    // mainArrays()
    // mainClasses()
    // mainCollection()
    // mainDataClasses()
    // mainEnum()
    // mainExtensions()
    // mainFilter()
    // mainFunction()
    // mainGenerics()
    // mainHelloWorld()
    // mainHigherOrderFunctions()
    // mainIf()
    // mainLambda()
    // mainLoops()
    // mainObject()
    // mainSealedClasses()
    // mainStream()
    // mainVariables()
    mainWhen()
}

fun commandLineArgs(args: Array<String>) {
    println("**** Command Line Arguments ***")

    if (args.isNotEmpty()) {
        println("Command line Arguments ==> ${args.contentToString()}")
        print("Command line args using loop ==> ")
        for (arg in args) {
            print("$arg, ")
        }
        println()
        println("Command line args joinToString ==> ${args.joinToString(", ")}")
    } else {
        println("No command line arguments passed.")
    }
}

fun readInputAndPrint() {
    println("**** Read Input from keyboard ***")

    // Prints a message to request input
    print("Enter any word: ")

    // Reads and stores the user input. For example: Happiness
    val yourWord = readln()

    println("Entered Input ==> $yourWord")
}