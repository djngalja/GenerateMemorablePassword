import kotlin.random.Random

const val SPECIAL_CHARS = "'-!\"#\$%&()*,./:;?@[]^_`{|}~+<=>"

fun main() {
    println("Enter text1:")
    val text1 = readln()
    println("Enter text2:")
    val text2 = readln()
    println("Enter password length:")
    val length = readln().toInt()
    println("Generated passwords:")
    for (i in 1..5) {
        val password = generatePassword(text1, text2, length)
        println("$i) $password")
    }
    test(text1, text2, length)
}

fun generatePassword(string1: String, string2: String, length: Int = 16): String {
    val list = combinedStrings(string1, string2)
    trimList(list, length)
    if (!containsDigit(list)) addDigitsOrLetters(list)
    if (!containsLetter(list)) addDigitsOrLetters(list, true)
    if (!containsUpperCase(list)) addLowerOrUpperCase(list, true)
    if (!containsLowerCase(list)) addLowerOrUpperCase(list)
    return joinedList(list)
}

fun combinedStrings(string1: String, string2: String): MutableList<String> {
    val list: MutableList<String>
    val list1 = string1.split(" ").toMutableList()
    val list2 = string2.split(" ").toMutableList()
    list = if (string1.isEmpty()) list2
    else if (string2.isEmpty()) list1
    else {
        list1.addAll(list2)
        list1
    }
    list.shuffle()
    return list
}

fun trimList(list: MutableList<String>, length: Int) {
    val tempList = mutableListOf<String>()
    copyList(list, tempList, length)
    list.clear()
    list.addAll(tempList)
}

fun copyList(
    list: MutableList<String>,
    tempList: MutableList<String>,
    length: Int
) {
    var tempLength = 0
    for (string in list) {
        tempLength += string.length + 1
        if (tempLength < length) tempList.add(string)
        else if (tempLength == length) {
            tempList.add(string)
            break
        } else { // tempLength > length
            val newString = string.dropLast(tempLength - length)
            tempList.add(newString)
            break
        }
    }
    if (tempLength < length) copyList(list, tempList, length - tempLength)
}

fun joinedList(list: MutableList<String>): String {
    var newString = ""
    for (string in list) newString += string + addChar(newString, list)
    return newString
}

fun containsLetter(list: MutableList<String>): Boolean {
    var containsLetter = false
    for (string in list) {
        for (char in string) {
            if (char.isLetter()) {
                containsLetter = true
                break
            }
        }
        if (containsLetter) break
    }
    return containsLetter
}

fun containsDigit(string: String): Boolean {
    var containsDigit = false
    for (char in string) {
        if (char.isDigit()) {
            containsDigit = true
            break
        }
    }
    return containsDigit
}

fun containsDigit(list: MutableList<String>): Boolean {
    var containsDigit = false
    for (string in list) {
        containsDigit = containsDigit(string)
        if (containsDigit) break
    }
    return containsDigit
}

fun containsUpperCase(string: String): Boolean {
    var containsUpperCase = false
    for (char in string) {
        if (char.isUpperCase()) {
            containsUpperCase = true
            break
        }
    }
    return containsUpperCase
}

fun containsUpperCase(list: MutableList<String>): Boolean {
    var containsUpperCase = false
    for (string in list) {
        containsUpperCase = containsUpperCase(string)
        if (containsUpperCase) break
    }
    return containsUpperCase
}

fun containsLowerCase(string: String): Boolean {
    var containsLowerCase = false
    for (char in string) {
        if (char.isLowerCase()) {
            containsLowerCase = true
            break
        }
    }
    return containsLowerCase
}

fun containsLowerCase(list: MutableList<String>): Boolean {
    var containsLowerCase = false
    for (string in list) {
        containsLowerCase = containsLowerCase(string)
        if (containsLowerCase) break
    }
    return containsLowerCase
}

fun containsSpecialChar(string: String): Boolean {
    var containsSpecialChar = false
    for (char in string) {
        if (char in SPECIAL_CHARS) {
            containsSpecialChar = true
            break
        }
    }
    return containsSpecialChar
}

fun containsSpecialChar(list: MutableList<String>): Boolean {
    var containsSpecialChar = false
    for (string in list) {
        containsSpecialChar = containsSpecialChar(string)
        if (containsSpecialChar) break
    }
    return containsSpecialChar
}

fun addDigitsOrLetters(list: MutableList<String>, addLetters: Boolean = false) {
    val replacements = mapOf('b' to '6', 'q' to '9', 'S' to '5', 'l' to '1', 'O' to '0')
    for ((oldChar, newChar) in replacements) {
        list.replaceAll { it.replace(oldChar, newChar, true) }
        if (containsDigit(list)) break
    }
    if (addLetters)
        for ((newChar, oldChar) in replacements)
            list.replaceAll { it.replace(oldChar, newChar, true)}
}

fun addLowerOrUpperCase(list: MutableList<String>, upperCase: Boolean = false) {
    list.replaceAll {
        var tempString = ""
        val p = 0.33 // probability
        if (upperCase) for (char in it) tempString += if (Random.nextBoolean(p)) char.uppercase() else char
        else for (char in it) tempString += if (Random.nextBoolean(p)) char.lowercase() else char
        tempString
    }
}

fun addChar(string: String, list: MutableList<String>): Char {
    return if (!(containsSpecialChar(string) || containsSpecialChar(list))) randomSpecialChar()
    else if (!(containsDigit(string) || containsDigit(list))) randomDigit()
    else if (!(containsUpperCase(string) || containsUpperCase(list))) randomUpperCaseLetter()
    else if (!(containsLowerCase(string) || containsLowerCase(list))) randomLowerCaseLetter()
    else randomChar()
}

fun Random.nextBoolean(p: Double) = nextDouble() < p

fun randomDigit(): Char = Random.nextInt(48, 58).toChar()

fun randomUpperCaseLetter(): Char = Random.nextInt(65, 91).toChar()

fun randomLowerCaseLetter(): Char = Random.nextInt(97, 123).toChar()

fun randomSpecialChar(): Char = SPECIAL_CHARS.random()

fun randomChar(): Char {
    return when (Random.nextInt(0, 4)) {
        0 -> randomDigit()
        1 -> randomUpperCaseLetter()
        2 -> randomLowerCaseLetter()
        else -> randomSpecialChar()
    }
}

fun test(string1: String, string2: String, length: Int) {
    println("\n__________Test__________")
    for (i in 0..10) {
        val password = generatePassword(string1, string2, length)
        println(password)
        println("length = ${password.length}")
        println(
            "d = ${containsDigit(password)}, u = ${containsUpperCase(password)}, l = ${containsLowerCase(password)}, s = ${
                containsSpecialChar(password)}"
        )
    }
}