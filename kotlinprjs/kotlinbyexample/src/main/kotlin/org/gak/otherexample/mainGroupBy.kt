package org.gak.otherexample

data class Person(val name: String, val age: Int, val city: String)

val people = listOf(
    Person("Alice", 25, "London"),
    Person("Bob", 30, "Paris"),
    Person("Charlie", 25, "London"),
    Person("David", 35, "New York"),
    Person("Eve", 30, "Paris")
)

fun mainGroupingBy() {
    // Grouping people by city and counting the number of people in each city
    val peopleByCityCount = people.groupingBy { it.city }.eachCount()
    println("People count by city: $peopleByCityCount")
    // People count by city: {London=2, Paris=2, New York=1}

    // Grouping people by age and collecting their names in a list
    val peopleByAgeNames = people.groupingBy { it.age }.fold(initialValue = mutableListOf<String>()) { acc, person ->
        acc.add(person.name)
        acc
    }
    println("People names by age: $peopleByAgeNames")
    // People names by age: {25=[Alice, Charlie], 30=[Bob, Eve], 35=[David]}

    mainGroupingBy1()
}

data class GithubCommits(val name: String, val commits: Int)

val gCommits = listOf(
    GithubCommits("zarechenskiy", 1249),
    GithubCommits("Kordyjan", 292),
    GithubCommits("goodwinnk", 129),
    GithubCommits("adkozlov", 65),
    GithubCommits("zarechenskiy", 51),
    GithubCommits("sellophane", 58)
)

fun mainGroupingBy1() {
    // Grouping people by name and commits
    val peopleCommits = gCommits.groupingBy { it.name }.aggregate { _, accumulator:Pair<String, Int>?, commit, first ->
        if (first) {
            Pair(commit.name, commit.commits)
        } else {
            Pair(commit.name, (accumulator?.second ?: 0) + commit.commits)
        }
    }
//        .aggregate {
//                   _, accumulator, commit, first ->
//            if (first) {
//                Pair(commit.name, commit.commits)
//            } else {
//                Pair(accumulator!!.first, accumulator.second + commit.
//                commits)
//            }
//            if (first) commit.commits else accumulator!! + commit.commits
//        }
    println("People commits: $peopleCommits")

    val peopleCommits1 = gCommits.groupingBy { it.name }.aggregate { key, value:Int?, commit, first ->
        if (first) {
           commit.commits
        } else {
            (value ?: 0) + commit.commits
        }
    }
    println("People commits: $peopleCommits1")

    val peopleCommits2 = gCommits.groupingBy { it.name }.aggregate { _, accumulator:GithubCommits?, commit, first ->
        if (first) {
            GithubCommits(commit.name, commit.commits)
        } else {
            GithubCommits(commit.name, (accumulator?.commits ?: 0) + commit.commits)
        }
    }.values.toList()
    println("People commits: $peopleCommits2")

}

//zarechenskiy ==> 1249
//Kordyjan ==> 292
//goodwinnk ==> 129
//adkozlov ==> 65
//sellophane ==> 58
//flire ==> 58
//bvfalcon ==> 49
//wpopielarski ==> 22
//Crazyjavahacking ==> 20
//ender93 ==> 18
//pjanczyk ==> 18
//gayanper ==> 15
//kamildoleglo ==> 8
//Maccimo ==> 8
//SimonScholz ==> 5
//AndreyOrlov ==> 4
//yole ==> 3
//vinni-au ==> 2
//erokhins ==> 2
//belovrv ==> 2
//yalishevant ==> 2
//abreslav ==> 1
//xosmig ==> 1
//donat ==> 1
//ilya-klyuchnikov ==> 1
//jrenner ==> 1
//Bananeweizen ==> 1
//olivierlemasle ==> 1
//PaGr0m ==> 1
//VladRassokhin ==> 1
//jonnyzzz ==> 54
//yanex ==> 47
//yole ==> 43
//sellmair ==> 15
//NataliaUkhorskaya ==> 13
//anton-bannykh ==> 8
//sashache ==> 6
//dromanov-jb ==> 6
//kamildoleglo ==> 5
//svtk ==> 5
//ansell ==> 4
//semoro ==> 4
//4u7 ==> 4
//nskvortsov ==> 3
//udalov ==> 2
//BarkingBad ==> 2
//abreslav ==> 2
//alexey-anufriev ==> 1
//magi82 ==> 1
//dzharkov ==> 1
//k4ml ==> 1
//MarcinAman ==> 1
//snodnipper ==> 1
//sdeleuze ==> 1
//cy6erGn0m ==> 1
//qwwdfsad ==> 1
//wilder ==> 1
//orende ==> 1
//shafirov ==> 1
//bashor ==> 312
//epabst ==> 38
//Schahen ==> 4
//erokhins ==> 4
//ilya-g ==> 3
//dzharkov ==> 2
//shabunc ==> 2
//udalov ==> 2
//abreslav ==> 1
//yole ==> 1
//skuzmich ==> 1
//svtk ==> 161
//dzharkov ==> 6
//romanmikhailovplaytika ==> 3
//mglukhikh ==> 3
//yole ==> 2
//vorburger ==> 2
//wild-lynx ==> 2
//cstew ==> 2
//abreslav ==> 2
//AlexeyTsvetkov ==> 1
//michelleheh ==> 1
//zarechenskiy ==> 1
//NataliaUkhorskaya ==> 1
//nelsonjchen ==> 1
//goodwinnk ==> 1
//rlindooren ==> 1
//romanmikhailov ==> 1
//nvlled ==> 1
//quad ==> 1
//tiembo ==> 1
//vadimsemenov ==> 1
//jxilt ==> 1
//oshai ==> 1
//satamas ==> 1
//singlepig ==> 1
//zaypen ==> 1
//BryanJacobs ==> 1
//alexandru-calinoiu ==> 1
//dhutchis ==> 1
//topka ==> 1
//kenshinji ==> 1
//ForNeVeR ==> 1
//hhariri ==> 1
//ilya-g ==> 1
//MasterXen ==> 1
//jakubczaplicki ==> 1
//jpd236 ==> 1
//chickenbane ==> 1
//jrodbx ==> 1
//jschear ==> 1
//jeversmann ==> 1
//m9rc1n ==> 1
//angst7 ==> 1
//yole ==> 472
//semoro ==> 377
//kamildoleglo ==> 361
//sellmair ==> 231
//MarcinAman ==> 226
//IgnatBeresnev ==> 200
//vmishenev ==> 198
//orangy ==> 160
//BarkingBad ==> 160
//Kordyjan ==> 154
//adam-enko ==> 103
//sellophane ==> 79
//whyoleg ==> 64
//pikinier20 ==> 55
//sswistun-vl ==> 53
//ottergottaott ==> 36
//dependabot[bot] ==> 35
//jonnyzzz ==> 34
//aSemy ==> 28
//berezinant ==> 26
//Goooler ==> 20
//atyrin ==> 12
//KrystianUjma ==> 9
//3flex ==> 9
//ingokegel ==> 8
//kisenka ==> 7
//zoobestik ==> 7
//SUPERCILEX ==> 7
//qwwdfsad ==> 6
//martinbonnin ==> 5
//nikpachoo ==> 5
//eskatos ==> 5
//ilya-g ==> 5
//alex2069 ==> 4
//rnett ==> 4
//AlejandraPedroza ==> 4
//romanowski ==> 4
//tiembo ==> 3
//hfhbd ==> 3
//msink ==> 3
//jisungbin ==> 3
//ddolovov ==> 3
//ColinHebert ==> 3
//liutikas ==> 3
//juliamcclellan ==> 2
//LiYing2010 ==> 2
//owengray-google ==> 2
//unly ==> 2
//mkondratek ==> 2
//vladimirshefer ==> 2
//surajsahani ==> 2
//StefMa ==> 2
//SimonMarquis ==> 2
//objcode ==> 2
//sarahhaggarty ==> 2
//robstoll ==> 2
//ansman ==> 2
//mikehearn ==> 2
//MattiasBuelens ==> 2
//lauzadis ==> 2
//HaasJona ==> 2
//igoriakovlev ==> 2
//chkpnt ==> 2
//d-ambatenne ==> 2
//asfalcone ==> 2
//chrisr3 ==> 2
//udalov ==> 2
//jush ==> 1