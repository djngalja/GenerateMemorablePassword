/*
* GENERATE A STRONG AND EASY-TO-REMEMBER PASSWORD.
*/

import kotlin.random.Random

const val SPECIAL_CHARS = "'-!\"#\$%&()*,./:;?@[]^_`{|}~+<=>" // special characters

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

/*
* Returns a password of type String generated using 2 strings and an integer (password length).
*/
fun generatePassword(string1: String, string2: String, length: Int): String {
    val list = combinedStrings(string1, string2)
    resizeList(list, length)
    if (!list.containsDigit()) addDigitsOrLetters(list)
    if (!list.containsLetter()) addDigitsOrLetters(list, true)
    if (!list.containsUpperCase()) addLowerOrUpperCase(list, true)
    if (!list.containsLowerCase()) addLowerOrUpperCase(list)
    val password = joinedList(list)
    return finalCheck(password)
}

/*
* Splits 2 strings by white spaces and joins the resulting substrings into a mutable list.
*/
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

/*
* Changes the size of the mutable list of type String to be joined into a string later so that the length of the resulting string matches the desired length.
*/
fun resizeList(list: MutableList<String>, length: Int) {
    val tempList = mutableListOf<String>()
    copyList(list, tempList, length)
    list.clear()
    list.addAll(tempList)
}

/*
* Copies strings from the mutable list of type String into a new list.
* The combined length of all strings in the new list with an additional character in between should match the length.
* If the original list is shorter, the function is called again.
* To match the length, the last string in the new list may be trimmed.
*/
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

/*
* Joins a mutable list of type String into a string.
* Each string from the list is followed by a random character.
* Returns the resulting string.
*/
fun joinedList(list: MutableList<String>): String {
    var newString = ""
    for (string in list) newString += string + addChar(newString, list)
    return newString
}

/*
* Returns `true` if this string is not a number.
* A String is considered to be a number if it contains only digits.
*/
fun String.isNotNumber(): Boolean {
    for (char in this) if (!char.isDigit()) return true
    return false
}

/*
* Returns `true` if this mutable list of type String contains at least 1 letter.
*/
fun MutableList<String>.containsLetter(): Boolean {
    for (string in this) for (char in string) if (char.isLetter()) return true
    return false
}

/*
* Returns `true` if this string contains at least 1 digit.
*/
fun String.containsDigit(): Boolean {
    for (char in this) if (char.isDigit()) return true
    return false
}

/*
* Returns `true` if this mutable list of type String contains at least 1 digit.
*/
fun MutableList<String>.containsDigit(): Boolean {
    for (string in this) if (string.containsDigit()) return true
    return false
}

/*
* Returns `true` if this string contains at least 1 upper case letter.
*/
fun String.containsUpperCase(): Boolean {
    for (char in this) if (char.isUpperCase()) return true
    return false
}

/*
* Returns `true` if this mutable list of type String contains at least 1 upper case letter.
*/
fun MutableList<String>.containsUpperCase(): Boolean {
    for (string in this) if (string.containsUpperCase()) return true
    return false
}

/*
* Returns `true` if this string contains at least 1 lower case letter.
*/
fun String.containsLowerCase(): Boolean {
    for (char in this) if (char.isLowerCase()) return true
    return false
}

/*
* Returns `true` if this mutable list of type String contains at least 1 lower case letter.
*/
fun MutableList<String>.containsLowerCase(): Boolean {
    for (string in this) if (string.containsLowerCase()) return true
    return false
}

/*
* Returns `true` if this string contains at least 1 special character.
* A character is considered to be special if it can be found in the SPECIAL_CHARS constant.
*/
fun String.containsSpecialChar(): Boolean {
    for (char in this) if (char in SPECIAL_CHARS) return true
    return false
}

/*
* Returns `true` if this mutable list of type String contains at least 1 special character.
* A character is considered to be special if it can be found in the SPECIAL_CHARS constant.
*/
fun MutableList<String>.containsSpecialChar(): Boolean {
    for (string in this) if (string.containsSpecialChar()) return true
    return false
}

/*
* If `addLetters` is set to `true`, replaces all occurrences of numbers 6, 9, 5, 1 and 0 with the letters b, q, s, l and o respectively, regardless of case.
* If there are no such numbers, no changes are made.
* Otherwise, a reverse algorithm is applied to  replace the letters with numbers.
* Letters are replaced only once, e.g. all occurrences of 'q' are replaced with the number 9, and no other changes are made.
*/
fun addDigitsOrLetters(list: MutableList<String>, addLetters: Boolean = false) {
    val replacements = mapOf('b' to '6', 'q' to '9', 'S' to '5', 'l' to '1', 'O' to '0')
    for ((oldChar, newChar) in replacements) {
        list.replaceAll { it.replace(oldChar, newChar, true) }
        if (list.containsDigit()) break
    }
    if (addLetters)
        for ((newChar, oldChar) in replacements)
            list.replaceAll { it.replace(oldChar, newChar, true) }
}

/*
* If `upperCase` is set to `true`, randomly replaces lower case letters in the list with respective upper case letters with the probability 1/3.
* Otherwise, randomly replaces upper case letters in the list with respective lower case letters with the probability 1/3.
*/
fun addLowerOrUpperCase(list: MutableList<String>, upperCase: Boolean = false) {
    list.replaceAll {
        var tempString = ""
        val p = 0.33 // probability
        if (upperCase) for (char in it) tempString += if (Random.nextBoolean(p)) char.uppercase() else char
        else for (char in it) tempString += if (Random.nextBoolean(p)) char.lowercase() else char
        tempString
    }
}

/*
* Returns a random special character if the string does not contain special characters.
* Returns a random digit if the string does not contain any digits.
* Returns a random upper case letter if the string does not contain any upper case letters.
* Returns a random lower case letter if the string does not contain any lower case letters.
* Returns a random character of one of the mentioned above types if the string contains characters of all the 4 types.
*/
fun addChar(string: String, list: MutableList<String>): Char {
    return if (!(string.containsSpecialChar() || list.containsSpecialChar())) randomSpecialChar()
    else if (!(string.containsDigit() || list.containsDigit())) randomDigit()
    else if (!(string.containsUpperCase() || list.containsUpperCase())) randomUpperCaseLetter()
    else if (!(string.containsLowerCase() || list.containsLowerCase())) randomLowerCaseLetter()
    else randomChar()
}

/*
* Replaces the 2nd digit, the 2nd lower or upper case letter or the 2nd special character (depending on whichever comes first) in the string with the given character.
* Returns an unchanged string if there are no repeating characters of the same type.
*/
fun String.replaceChar(char: Char): String {
    val builder = StringBuilder(this)
    var digit = 0
    var lLetter = 0
    var uLetter = 0
    var sChar = 0
    for (i in this.indices) {
        if (this[i].isDigit()) digit++
        else if (this[i].isLowerCase()) lLetter++
        else if (this[i].isUpperCase()) uLetter++
        else sChar++
        if (digit == 2 || lLetter == 2 || uLetter == 2 || sChar == 2) {
            builder[i] = char
            break
        }
    }
    return builder.toString()
}

/*
* Checks whether the string contains at least 1 digit.
* If not, replaces the 1st repeating character (the 2nd special character, the 2nd lower case letter or the 2nd upper case letter) with a random digit.
* The same algorithm repeated 3 more times for lower and upper case letters, as well as special characters.
* Returns an unchanged string, if the original string contains all 4 types of characters.
*/
fun finalCheck(string: String): String {
    var newString = string
    if (!newString.containsDigit()) newString = newString.replaceChar(randomDigit())
    if (!newString.containsLowerCase()) newString = newString.replaceChar(randomLowerCaseLetter())
    if (!newString.containsUpperCase()) newString = newString.replaceChar(randomUpperCaseLetter())
    if (!newString.containsSpecialChar()) newString = newString.replaceChar(randomSpecialChar())
    return newString
}

/*
* Generates `true` with the probability p.
*/
fun Random.nextBoolean(p: Double) = nextDouble() < p

/*
* Returns a random digit of type Char.
*/
fun randomDigit(): Char = Random.nextInt(48, 58).toChar()

/*
* Returns a random upper case letter.
*/
fun randomUpperCaseLetter(): Char = Random.nextInt(65, 91).toChar()

/*
* Returns a random lower case letter.
*/
fun randomLowerCaseLetter(): Char = Random.nextInt(97, 123).toChar()

/*
* Returns a random special character.
* A character is considered to be special if it can be found in the SPECIAL_CHARS constant.
*/
fun randomSpecialChar(): Char = SPECIAL_CHARS.random()

/*
* Returns a random Char.
* It can be a digit (of type Char), a special character or a letter.
* A character is considered to be special if it can be found in the SPECIAL_CHARS constant.
*/
fun randomChar(): Char {
    return when (Random.nextInt(0, 4)) {
        0 -> randomDigit()
        1 -> randomUpperCaseLetter()
        2 -> randomLowerCaseLetter()
        else -> randomSpecialChar()
    }
}