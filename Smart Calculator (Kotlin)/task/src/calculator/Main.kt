package calculator
import kotlin.system.exitProcess
import java.math.BigInteger

fun math(inputString: String) {
    fun power(input: MutableList<String>): MutableList<String> {
        val indexOfPower = input.indexOf("^")
        val expression = input[indexOfPower - 1].toBigInteger()
        val power = input[indexOfPower + 1].toInt()
        val resultOfPower = expression.pow(power)
        var output = mutableListOf<String>()
        if (input.size == 3) {
            output += resultOfPower.toString()
        } else {
            output = input
            output.subList(indexOfPower - 1, indexOfPower + 2).clear()
            output.add(indexOfPower - 1, resultOfPower.toString())
        }
        return output
    }

    fun multiplication(input: MutableList<String>): MutableList<String> {
        val indexOfMulti = input.indexOf("*")
        val multi1 = input[indexOfMulti - 1].toBigInteger()
        val multi2 = input[indexOfMulti + 1].toBigInteger()
        val resultOfMutli = multi1 * multi2
        var output = mutableListOf<String>()
        if (input.size == 3) {
            output += resultOfMutli.toString()
        } else {
            output = input
            output.subList(indexOfMulti - 1, indexOfMulti + 2).clear()
            output.add(indexOfMulti - 1, resultOfMutli.toString())
        }
        return output
    }

    fun division(input: MutableList<String>): MutableList<String> {
        val indexOfDiv = input.indexOf("/")
        val divisible = input[indexOfDiv - 1].toBigInteger()
        val divider = input[indexOfDiv + 1].toBigInteger()
        val resultOfDivision = divisible / divider
        var output = mutableListOf<String>()
        if (input.size == 3) {
            output += resultOfDivision.toString()
        } else {
            output = input
            output.subList(indexOfDiv - 1, indexOfDiv + 2).clear()
            output.add(indexOfDiv - 1, resultOfDivision.toString())
        }
        return output
    }

    fun addition(input: MutableList<String>): MutableList<String> {

        val indexOfAdd = input.indexOf("+")
        var i = indexOfAdd
        while (input[i].toBigIntegerOrNull() == null) {
            if (input[i] == "+") {
                val previous = input[i - 1]
                if (input[i] == previous) {
                    input.removeAt(i)
                } else {
                    i++
                }
            } else {
                i++
            }
        }

        val add1 = input[indexOfAdd - 1].toBigInteger()
        val add2 = input[indexOfAdd + 1].toBigInteger()
        val resultOfAddition = add1 + add2
        var output = mutableListOf<String>()
        if (input.size == 3) {
            output += resultOfAddition.toString()
        } else {
            output = input
            output.subList(indexOfAdd - 1, indexOfAdd + 2).clear()
            output.add(indexOfAdd - 1, resultOfAddition.toString())
        }
        return output
    }

    fun subtraction(input: MutableList<String>): MutableList<String> {

        var count = 1
        val indexOfOperator = input.indexOf("-")
        var i = indexOfOperator
        while (input[i].toBigIntegerOrNull() == null) {
            if (input[i] == "-") {
                val previous = input [i - 1]
                if (input[i] == previous) {
                    input.removeAt(i)
                    count++
                } else {
                    i++
                }
            } else {
                i++
            }
        }

        val exp1 = input[indexOfOperator - 1].toBigInteger()
        val exp2 = input[indexOfOperator + 1].toBigInteger()
        val resultOfSubtraction: BigInteger = if (count > 0 && count % 2 == 0) {
            exp1 + exp2
        } else {
            exp1 - exp2
        }
        var output = mutableListOf<String>()
        if (input.size == 3) {
            output += resultOfSubtraction.toString()
        } else {
            output = input
            output.subList(indexOfOperator - 1, indexOfOperator + 2).clear()
            output.add(indexOfOperator - 1, resultOfSubtraction.toString())
        }
        return output
    }


    fun choice(input: MutableList<String>): MutableList<String> {
        return when {
            input.contains("^") -> power(input)
            input.contains("*") || input.contains("/")  -> {
                if (input.contains("*") && !input.contains("/")) {
                    return multiplication(input)
                } else if (!input.contains("*") && input.contains("/")) {
                    return division(input)
                } else {
                    val indexOfMulti = input.indexOf("*")
                    val indexOfDiv = input.indexOf("/")
                    if (indexOfMulti < indexOfDiv) {
                        return multiplication(input)
                    } else {
                        return division(input)
                    }
                }
            }
            input.contains("+")  || input.contains("-") -> {
                if (input.contains("+") && !input.contains("-")) {
                    return addition(input)
                } else if (!input.contains("+") && input.contains("-")) {
                    return subtraction(input)
                } else {
                    val indexOfPlus = input.indexOf("+")
                    val indexOfMinus = input.indexOf("-")
                    if (indexOfPlus < indexOfMinus) {
                        return addition(input)
                    } else {
                        return subtraction(input)
                    }
                }
            }
            else -> input
        }
    }

    fun isExpressionInvalid(expression: String): Boolean {
        var openBracketsCount = 0
        var prevChar: Char? = null

        for (char in expression) {
            when (char) {
                '(' -> openBracketsCount++
                ')' -> {
                    if (openBracketsCount == 0) {
                        return true
                    }
                    openBracketsCount--
                }
                '*', '/' -> {
                    if (prevChar == '*' || prevChar == '/') {
                        return true
                    }
                }
            }
            prevChar = char
        }

        return openBracketsCount != 0
    }

    if (isExpressionInvalid(inputString)) {
        println("Invalid expression")
        return
    }

    var inputList = mutableListOf<String>()
    val regex = Regex("-?\\d+|[+\\-*/^()]")
    regex.findAll(inputString).forEach { matchResult ->
        val value = matchResult.value
        if (value.startsWith("-") && value.length > 1) {
            inputList.add(value)
        } else {
            inputList.add(value)
        }
    }

    val operatorRegex = "[+\\-*^/]".toRegex()

    while (inputList.contains("(")) {
        val startIndex = inputList.lastIndexOf("(")
        val endIndex = inputList.subList(startIndex + 1, inputList.size).indexOf(")") + startIndex + 1
        val insideBraces = inputList.subList(startIndex + 1, endIndex)
        var resultInsideBraces: MutableList<String>

        fun calculationInsideBraces(input: MutableList<String>): MutableList<String> {
            return if (input.any { element -> operatorRegex.containsMatchIn(element) }) {
                resultInsideBraces = choice(input)
                calculationInsideBraces(resultInsideBraces)
            } else {
                input
            }
        }

        val oldList = mutableListOf<String>()
        oldList.addAll(inputList)
        resultInsideBraces = calculationInsideBraces(insideBraces)

        oldList.subList(startIndex, endIndex + 1).clear()
        oldList.add(startIndex, resultInsideBraces.joinToString(""))
        inputList = oldList
    }


    while (inputList.size != 1) {
        inputList = choice(inputList)
    }

    println(inputList.joinToString(""))
}

fun help() {
    println("""The program is a calculator. It can work with very large numbers.
It is able to memorize the values of variables and read expressions both entered using numbers and using variables. 
At the moment, the program is able to work with the following operators: 
exponentiation, multiplication, division, addition, subtraction, 
and also processes expressions enclosed in parentheses.""".trimMargin())
}

fun exit() {
    println("Bye!")
    exitProcess(1)
}
fun command(inputCommand: String) {
    when (inputCommand) {
        "/help" -> help()
        "/exit" -> exit()
        else -> println("Unknown command")
    }
}


fun main() {

    val declaredVariables = mutableMapOf<String, BigInteger>()

    fun assignment(assignmentInput: String) {

        val regexWithDigit = "\\s*[a-zA-Z]+\\s*=\\s*-*\\d*\\s*".toRegex()
        val regexWithoutDigit = "\\s*[a-zA-Z]+\\s*=\\s*[a-zA-Z]+\\s*".toRegex()

        if (assignmentInput.matches(regexWithDigit)) {
            val nameOfVariable = assignmentInput.replace(" ","").split("=")[0]
            val valueOfVariable = assignmentInput.replace(" ","").split("=")[1].toBigInteger()
            declaredVariables[nameOfVariable] = valueOfVariable
        } else  if (assignmentInput.matches(regexWithoutDigit)) {
            val newVariable = assignmentInput.replace(" ","").split("=")[0]
            val valueOfVariable = assignmentInput.replace(" ","").split("=")[1]
            fun searchOfValue(input: String): BigInteger {
                var variable = input
                if (variable.toBigIntegerOrNull() == null) {
                    return try {
                        variable = declaredVariables[variable].toString()
                        variable.toBigInteger()
                    } catch (e: NumberFormatException) {
                        println("Unknown variable")
                        BigInteger.ZERO
                    }

                }
                return variable.toBigInteger()
            }
            declaredVariables[newVariable] = searchOfValue(valueOfVariable)
        } else if (!assignmentInput.replace(" ","").split("=")[0].matches("[a-zA-Z]+".toRegex())) {
            println("Invalid identifier")
        } else {
            println("Invalid assignment")
        }
    }

    fun replaceVariables(inputExpression: String, map: Map<String, BigInteger>): String {
        val variablePattern = "\\b([a-zA-Z]+)\\b".toRegex()
        val replacedExpression = variablePattern.replace(inputExpression) {result ->
            val variableName = result.value
            if (!map.containsKey(variableName))  {
                println("Unknown variable")
            }
            val variableValue = map[variableName]
            variableValue.toString()
        }
        return replacedExpression
    }


    fun calculation(inputForCalculation: String) {

        if (inputForCalculation.toBigIntegerOrNull() != null)  {
            println(inputForCalculation)
            return
        }

        if (inputForCalculation.matches("""^[-+*/()\d\s^.]+$""".toRegex())) {
            math(inputForCalculation)
            return
        }

        if (inputForCalculation.matches("[a-zA-Z]+".toRegex())) {
            if (declaredVariables[inputForCalculation] != null) {
                println(declaredVariables[inputForCalculation])
                return
            } else {
                println("Unknown variable")
                return
            }
        }

        math(replaceVariables(inputForCalculation, declaredVariables))

    }

    while (true) {
        val inputString = readln()
        when {
            inputString.startsWith('/') -> command(inputString)
            inputString.contains('=') -> assignment(inputString)
            inputString.isBlank() -> continue
            else -> calculation(inputString)
        }
    }
}






