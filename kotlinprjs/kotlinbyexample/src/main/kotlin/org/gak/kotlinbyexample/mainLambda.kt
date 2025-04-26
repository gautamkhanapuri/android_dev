package org.gak.kotlinbyexample

fun mainLambda() {

    // A lambda with explicit types everywhere and definition in curly braces,
    val
            upperCase1
            :
                (String) -> String
            =
        { str: String -> str.uppercase() } // 1

    // Type inference inside lambda: the type of the lambda parameter is inferred
    // from the type of the variable it's assigned to.
    val upperCase2: (String) -> String = { str -> str.uppercase() }         // 2

    // Type inference of the variable is inferred from the type of the lambda
    // parameter and return value
    val upperCase3 = { str: String -> str.uppercase() }                     // 3

    // the compiler has no chance to infer the input and output type
    // val upperCase4 = { str -> str.uppercase() }                          // 4

    // For lambdas with a single parameter, use the implicit it variable.
    val upperCase5: (String) -> String = { it.uppercase() }                 // 5

    // lambda with a single function call can use function pointers (::)
    val upperCase6: (String) -> String = String::uppercase                  // 6

    println("Lambda type in declaration and definition ==> ${upperCase1("hello")}")
    println("Lambda type in declaration ==> ${upperCase2("hello")}")
    println("Lambda type in definition ==> ${upperCase3("hello")}")
    println("Lambda type in declaration with it ==>  ${upperCase5("hello")}")
    println("Lambda type in declaration with function call ==> ${upperCase6("hello")}")

}