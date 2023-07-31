import kotlin.random.Random

fun main() {
    println("Enter text1:")
    val text1 = "Peach cake with brown sugar icing"
    println("Enter text2:")
    val text2 = "Dancin' is what to do"
    println("Enter password length:")
    println("Generated passwords:\n")
    for (i in 0..4) println(createPassword(text1, text2, 20))
}

fun createPassword(string1: String, string2: String, length: Int = 16): String {
    val list = combineStrings(string1, string2)
    val remainingChars = createNewList(list, length)
    var newString = createNewString(list, remainingChars)
    newString = addDigits(newString)
    return addCase(newString)
}

fun addDigits(string: String): String {
    var newString = string
    val rep = mapOf('b' to '6', 'q' to '9', 'S' to '5', 'l' to '1', 'O' to '0')
    rep.forEach { (oldChar, newChar) -> newString = newString.replace(oldChar, newChar, true) }
    return newString
}

fun addCase(string: String): String {
    var newString = ""
    string.forEach { newString += if (Random.nextBoolean()) it.uppercase() else it.lowercase() }
    return newString
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

fun combineStrings (string1: String, string2: String): MutableList<String> {
    val split1 = string1.split(" ").toMutableList()
    val split2 = string2.split(" ")
    split1.addAll(split2)
    split1.shuffle()
    return split1
}

fun createNewList(list: MutableList<String>, length: Int): Int {
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

fun createNewString(list: MutableList<String>, remaining: Int): String {
    var newString = ""
    for (i in list.indices) newString += list[i] + randomChar()
    if (remaining == -1) newString = newString.dropLast(1)
    else if (remaining > 0) {
        for (i in 1..remaining) {
            when (Random.nextBoolean()) {
                true -> newString += randomChar()
                else -> newString = randomChar() + newString
            }
        }
    }
    return newString
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