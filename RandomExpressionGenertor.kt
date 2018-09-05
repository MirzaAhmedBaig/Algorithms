import java.util.*
import javax.script.ScriptEngineManager

class RandomExpressionGenertor {
    private val operatersArray = arrayOf('+', '-', '/', '*')
    private var inputArrayList = ArrayList<String>()
    private var expressionList = ArrayList<String>()
    private var numbersString = ""
    private var expression = ""
    private var result = 0

    constructor(isAdvanced: Boolean) {
        if (isAdvanced) {

        } else {
            fillInputArray()
            getRandomizedNumbersFromInputArray()
//            generateExpression()
        }
    }

    private fun fillInputArray() {
        inputArrayList = ArrayList()
        (0..5).forEach {
            val random = (1..5).random().toString()
            inputArrayList.add(random)
            numbersString += random

        }
    }

    private fun getRandomizedNumbersFromInputArray() {
        expressionList = ArrayList()
        while (numbersString.isNotEmpty()) {
            expressionList.add(getRandomNumberString())
        }

        expression.forEach {
            System.out.print("\t $it")
        }
    }

    fun generateRandomizedExpression() {
        expressionList.forEachIndexed { index, it ->
            if (index != expressionList.lastIndex) {
                val operator = getRandomOperator()
                if (operator == '/') {

                } else {
                    expression = expression + expressionList[index] + operator.toString()
                }
            }
        }
    }

    fun getRandomizedArray(): ArrayList<String> {
        return expressionList
    }

    private fun generateExpression() {
        expressionList = ArrayList()
        while (numbersString.isNotEmpty()) {
            expressionList.add(getRandomNumberString())
            expressionList.add(getRandomOperator().toString())
        }
        expressionList.removeAt(expressionList.lastIndex)

        var secondDivIndex = -1
        if (expressionList.contains("/")) {
            val count = expressionList.count { it == "/" }
            if (count >= 2) {
                (0 until count - 2).forEach {
                    expressionList[expressionList.indexOf("/")] = "+"
                }

                secondDivIndex = expressionList.indexOfLast { it == "/" }
                val temp = expressionList[secondDivIndex - 1]
                if (temp < expressionList[secondDivIndex + 1]) {
                    expressionList[secondDivIndex - 1] = expressionList[secondDivIndex + 1]
                    expressionList[secondDivIndex + 1] = temp
                }


                val secondAns = expressionList[secondDivIndex - 1].toFloat() / expressionList[secondDivIndex + 1].toFloat()
                if (secondAns.toInt().toString().plus(".0") != secondAns.toString()) {
                    expressionList[secondDivIndex - 1] = (expressionList[secondDivIndex + 1].toInt() * 2).toString()
                }
            }

            var firstDivIndex = expressionList.indexOf("/")
            if (secondDivIndex - firstDivIndex == 2) {
                expressionList[firstDivIndex] = "-"

            } else {
                var temp = expressionList[firstDivIndex - 1]
                if (temp < expressionList[firstDivIndex + 1]) {
                    expressionList[firstDivIndex - 1] = expressionList[firstDivIndex + 1]
                    expressionList[firstDivIndex + 1] = temp
                }


                val firstAns = expressionList[firstDivIndex - 1].toFloat() / expressionList[firstDivIndex + 1].toFloat()
                if (firstAns.toInt().toString().plus(".0") != firstAns.toString()) {
                    expressionList[firstDivIndex - 1] = (expressionList[firstDivIndex + 1].toInt() * 2).toString()
                }
            }


        }
        expressionList.forEach {
            expression += it
        }
        generateResult(expression)

    }

    private fun generateResult(exp: String) {
        result = getExpressionResult(exp)
    }

    private fun getRandomOperator(): Char {
        val chars = arrayOf('+', '-', '/', '*')
        return chars[(0 until chars.lastIndex).random()]
    }

    private fun getRandomNumberString(): String {
        val randomCount = Math.min((1..2).random(), numbersString.lastIndex)
        var numberString = ""
        if (randomCount > 0) {
            (0 until randomCount).forEach {
                val random = (0..numbersString.lastIndex).random()
                numberString += numbersString.toCharArray()[random]
                numbersString = numbersString.removeRange(random, random + 1)
            }
        } else {
            numberString += numbersString
            numbersString = ""
        }
        return numberString
    }

    fun getResult(): Int {
        return result
    }

    fun getInputArray(): ArrayList<String> {
        return inputArrayList
    }

    fun getExpression(): String {
        return expression
    }

    fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) + start

    private fun getExpressionResult(exp: String): Int {
        val mgr = ScriptEngineManager()
        val engine = mgr.getEngineByName("JavaScript")
        return engine.eval(exp).toString().toInt()
    }


}