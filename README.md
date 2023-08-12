## Generate Memorable Password

<p style="font-size:20px;"> Run this Kotlin code to generate a strong password that is humanly possible to memorise. </p>

### Input

A user is expected to enter 2 strings and an integer.

**String 1:**
This string can be anything: a word, a number, a random sequence of characters or a normal sentence.
However, the generated password will be easier to remember if the input is easy to remember, too.
A line from a song, a quotation, a celebrity's name or a favourite word are all good options.

**String 2:**
To make the results less predictable and more interesting, the user is asked to do it again.

**Integer:**
The user can enter any reasonable length.
If the input is not an integer, a negative number or empty, the length of the generated password is set to 16.

### Output

A strong password is long, and it contains digits, lower and upper case letters as well as special characters.
Whether a password is easy-to-remember, or not, is subjective. For this reason, 5 options are generated for the user to
choose from.

### Sample input and output

**Long password**<br />
Enter text1: *esteemed patroness*<br />
Enter text2: *excellent boiled potatoes*<br />
Enter password length: *16*<br />
Generated passwords:

1) `ExcElleNT)6oile#`
2) `EsTeemEd.6oIlEd9`
3) `poTatoEs(6oilEDS`
4) `6oiLed-estEEmed.`
5) `6oILed?eXcEllenv`

**Short password**<br />
Enter password length: *8*<br />
Generated passwords:

1) `PAtr0Ne]`
2) `pAtr0ne:`
3) `patR0nE(`
4) `E5teeme'`
5) `p0Dat0e#`

**Very short password**<br />
Enter password length: *4*<br />
Generated passwords:

1) `6oQ,`
2) `6oI<`
3) `6oI"`
4) `eX3[`
5) `p0H_`

### Algorithm

*Step 1:*
The strings are combined and split into individual words that are shuffled.
<br /><br />*Step 2:*
The words are copied to a new list to be joined to a password later.
To match the desired length, words may be omitted or repeated. One word can be trimmed.
<br /><br />*Step 3:*
If there are no digits, some of the letters can be replaced with them.
For example, 'b' can be replaced with '6', regardless of case.
Letters are replaced only once so that the password remains relatively easy to remember
(e.g. all occurrences of  'b' and 'B' are replaced with '6', but other letters remain unchanged).
A reverse algorithm is applied in case there are only numbers and no letters.

If there are only lower case letters, some of them are replaced with the respective upper case letters.
They are replaced randomly, with the probability 1/3.
A higher probability of replacing would make the resulting password harder to memorise.
A lower probability would make little difference for shorter passwords.
A reverse algorithm is applied in case there are only upper case letters.
<br /><br />*Step 4:*
The words are joined to form a password. Each word is followed by a random digit (if there are no digits),
a random special character (if there are no special characters) or a random lower or upper case letters,
if there are no lower or upper case letters. Otherwise, a random character (out of these 4 options) is added.
<br /><br />*Step 5:*
The resulting password is checked one last time to make sure that it contains at least 1
digit, 1 special character, 1 lower and 1 upper case letter (unless the password length is shorter than 4).
If something is missing, one of the repeating characters (e.g. the 2nd digit, or the 2nd lower case letter) is replaced.

