import kotlin.random.Random

const val SPECIAL_CHARS = "'-!\"#\$%&()*,./:;?@[]^_`{|}~+<=>"

fun main() {
    println("Enter text1:")
    val text1 = readln()
    println("Enter text2:")
    val text2 = readln()
    println("Enter password length:")
    val text3 = readln()
    val length = if (text3.isBlank() || text3.isNotNumber()) 16 else text3.toInt()
    println("Generated passwords:")
    for (i in 1..5) {
        val password = generatePassword(text1, text2, length)
        println("$i) $password")
    }
}

fun generatePassword(string1: String, string2: String, length: Int): String {
    val list = combinedStrings(string1, string2)
    trimList(list, length)
    if (!list.containsDigit()) addDigitsOrLetters(list)
    if (!list.containsLetter()) addDigitsOrLetters(list, true)
    if (!list.containsUpperCase()) addLowerOrUpperCase(list, true)
    if (!list.containsLowerCase()) addLowerOrUpperCase(list)
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

/*
* Returns `true` if this String is not a number.
* A String is considered to be a number if it contains only digits.
*/
fun String.isNotNumber(): Boolean {
    for (char in this) if (!char.isDigit()) return true
    return false
}

fun MutableList<String>.containsLetter(): Boolean {
    for (string in this) for (char in string) if (char.isLetter()) return true
    return false
}

fun String.containsDigit(): Boolean {
    for (char in this) if (char.isDigit()) return true
    return false
}

fun MutableList<String>.containsDigit(): Boolean {
    for (string in this) if (string.containsDigit()) return true
    return false
}

fun String.containsUpperCase(): Boolean {
    for (char in this) if (char.isUpperCase()) return true
    return false
}

fun MutableList<String>.containsUpperCase(): Boolean {
    for (string in this) if (string.containsUpperCase()) return true
    return false
}

fun String.containsLowerCase(): Boolean {
    for (char in this) if (char.isLowerCase()) return true
    return false
}

fun MutableList<String>.containsLowerCase(): Boolean {
    for (string in this) if (string.containsLowerCase()) return true
    return false
}

fun String.containsSpecialChar(): Boolean {
    for (char in this) if (char in SPECIAL_CHARS) return true
    return false
}

fun MutableList<String>.containsSpecialChar(): Boolean {
    for (string in this) if (string.containsSpecialChar()) return true
    return false
}

fun addDigitsOrLetters(list: MutableList<String>, addLetters: Boolean = false) {
    val replacements = mapOf('b' to '6', 'q' to '9', 'S' to '5', 'l' to '1', 'O' to '0')
    for ((oldChar, newChar) in replacements) {
        list.replaceAll { it.replace(oldChar, newChar, true) }
        if (list.containsDigit()) break
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
    return if (!(string.containsSpecialChar() || list.containsSpecialChar())) randomSpecialChar()
    else if (!(string.containsDigit() || list.containsDigit())) randomDigit()
    else if (!(string.containsUpperCase() || list.containsUpperCase())) randomUpperCaseLetter()
    else if (!(string.containsLowerCase() || list.containsLowerCase())) randomLowerCaseLetter()
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