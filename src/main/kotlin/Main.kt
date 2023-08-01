import kotlin.random.Random

fun main() {
    //println("Enter text1:")
    val text1 = "Peach cake with brown sugar icing"
    //println("Enter text2:")
    val text2 = "Dancin' is what to do"
    //println("Enter password length:")
    val length = 20
    println("Generated passwords:")
    for (i in 1..5) {
        val password = generatePassword(text1, text2, length)
        println("$i) $password")
    }
    //Testing:
    generatePasswordTest(text1, text2)
}

fun generatePassword(string1: String, string2: String, length: Int = 16): String {
    val list = combineStrings(string1, string2)
    val remainingChars = trimList(list, length)
    if (!containsDigit(list)) addDigits(list)
    if (!containsUpperCase(list)) addUpperCase(list)
    return joinList(list, remainingChars)
}

fun randomUpperCaseLetter(): Char = Random.nextInt(65, 91).toChar()

fun randomLowerCaseLetter(): Char = Random.nextInt(97, 123).toChar()

fun randomDigit(): Char = Random.nextInt(48, 58).toChar()

fun randomSpecialChar(): Char = "'-!\"#\$%&()*,./:;?@[]^_`{|}~+<=>".random()

fun randomChar(): Char {
    return when (Random.nextInt(0, 4)) {
        0 -> randomDigit()
        1 -> randomUpperCaseLetter()
        2 -> randomLowerCaseLetter()
        else -> randomSpecialChar()
    }
}

fun combineStrings(string1: String, string2: String): MutableList<String> {
    val split1 = string1.split(" ").toMutableList()
    val split2 = string2.split(" ")
    split1.addAll(split2)
    split1.shuffle()
    return split1
}

fun trimList(list: MutableList<String>, length: Int): Int {
    val newList = mutableListOf<String>()
    var len = 0
    for (string in list) {
        if (len + string.length <= length) {
            len += string.length + 1
            newList.add(string)
        }
    }
    list.retainAll(newList)
    return length - len
}

fun joinList(list: MutableList<String>, remaining: Int): String {
    var newString = ""
    for (i in list.indices) newString += list[i] + randomSpecialChar()
    if (remaining == -1) newString = newString.dropLast(1)
    else if (remaining > 0) {
        for (i in 1..remaining) {
            if (!containsDigit(newString)) newString += randomDigit()
            else if (!containsUpperCase(newString)) newString = randomUpperCaseLetter() + newString
            else if (!containsLowerCase(newString)) newString = randomLowerCaseLetter() + newString
            else newString += randomChar()
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

fun containsSpecialChar(string: String): Boolean {
    var containsSpecialChar = false
    val spChars = "'-!\"#\$%&()*,./:;?@[]^_`{|}~+<=>"
    for (char in string) {
        for (i in spChars) {
            if (i == char) {
                containsSpecialChar = true
                break
            }
        }
    }
    return containsSpecialChar
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

fun addDigits(list: MutableList<String>) {
    val replacements = mapOf('b' to '6', 'q' to '9', 'S' to '5', 'l' to '1', 'O' to '0')
    for ((oldChar, newChar) in replacements) {
        list.replaceAll { it.replace(oldChar, newChar, true) }
    }
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

fun addUpperCase(list: MutableList<String>) {
    list.replaceAll { addUpperCase(it) }
}

fun addUpperCase(string: String): String {
    var newString = ""
    for (char in string) {
        newString += if (Random.nextBoolean()) char.uppercase() else char
    }
    return newString
}

fun generatePasswordTest(string1: String, string2: String) {
    println("\n__________Test__________")
    for (i in 0..10) {
        val password = generatePassword(string1, string2)
        println(password)
        println("d = ${containsDigit(password)}, u = ${containsUpperCase(password)}, l = ${containsLowerCase(password)}, s = ${
            containsSpecialChar(password)}")
    }
}