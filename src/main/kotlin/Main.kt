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
    val remainingChars = trimList(list, length)
    if (!containsDigit(list)) addDigits(list)
    if (!containsUpperCase(list)) addUpperCase(list)
    if (!containsLowerCase(list)) addLowerCase(list)
    return joinedList(list, remainingChars)
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

fun trimList(list: MutableList<String>, length: Int): Int {
    val tempList = mutableListOf<String>()
    var len = 0
    for (string in list) {
        if (len + string.length <= length) {
            len += string.length + 1
            tempList.add(string)
        }
    }
    if (list.isNotEmpty() && tempList.isEmpty()) {
        var count = 0
        if (!containsDigit(list[0])) count++
        if (!containsSpecialChar(list[0])) count++
        if (!containsUpperCase(list[0])) count++
        if (!containsLowerCase(list[0])) count++
        val n = list[0].length - length + count
        val tempString = list[0].dropLast(n)
        tempList.add(tempString)
        len = tempString.length
    }
    list.clear()
    list.addAll(tempList)
    return length - len
}

fun joinedList(list: MutableList<String>, remaining: Int): String {
    var newString = ""
    for (i in list.indices) newString += list[i] + addChar(newString, list)
    if (remaining == -1) newString = newString.dropLast(1)
    else if (remaining > 0) {
        for (i in 1..remaining) {
            when (Random.nextBoolean()) {
                true -> newString += addChar(newString)
                else -> newString = addChar(newString) + newString
            }
        }
    }
    return newString
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
        for (char in string) {
            if (char.isDigit()) {
                containsDigit = true
                break
            }
        }
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
        for (char in string) {
            if (char.isUpperCase()) {
                containsUpperCase = true
                break
            }
        }
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
        for (char in string) {
            if (char.isLowerCase()) {
                containsLowerCase = true
                break
            }
        }
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
        for (char in string) {
            if (char in SPECIAL_CHARS) {
                containsSpecialChar = true
                break
            }
        }
        if (containsSpecialChar) break
    }
    return containsSpecialChar
}

fun addDigits(list: MutableList<String>) {
    val replacements = mapOf('b' to '6', 'q' to '9', 'S' to '5', 'l' to '1', 'O' to '0')
    for ((oldChar, newChar) in replacements) {
        list.replaceAll { it.replace(oldChar, newChar, true) }
    }
}

fun addUpperCase(list: MutableList<String>) {
    list.replaceAll {
        var temp = ""
        for (char in it) temp += if (Random.nextBoolean()) char.uppercase() else char
        temp
    }
}

fun addLowerCase(list: MutableList<String>) {
    list.replaceAll{
        var temp = ""
        for (char in it) temp += if (Random.nextBoolean()) char.lowercase() else char
        temp
    }
}

fun addChar(string: String): Char {
    return if (!containsSpecialChar(string)) randomSpecialChar()
    else if (!containsDigit(string)) randomDigit()
    else if (!containsUpperCase(string)) randomUpperCaseLetter()
    else if (!containsLowerCase(string)) randomLowerCaseLetter()
    else randomChar()
}

fun addChar(string: String, list: MutableList<String>): Char {
    return if (!(containsSpecialChar(string) || containsSpecialChar(list))) randomSpecialChar()
    else if (!(containsDigit(string) || containsDigit(list))) randomDigit()
    else if (!(containsUpperCase(string) || containsUpperCase(list))) randomUpperCaseLetter()
    else if (!(containsLowerCase(string) || containsLowerCase(list))) randomLowerCaseLetter()
    else randomChar()
}

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
        println(
            "d = ${containsDigit(password)}, u = ${containsUpperCase(password)}, l = ${containsLowerCase(password)}, s = ${
                containsSpecialChar(password)}"
        )
    }
}