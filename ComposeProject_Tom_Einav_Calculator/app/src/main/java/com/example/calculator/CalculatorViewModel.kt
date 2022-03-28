package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.calculator.ui.theme.CalculatorFontColor
import com.notkamui.keval.Keval
import java.lang.Exception

class CalculatorViewModel: ViewModel(){

    private var _canInsertDecimalPoint by mutableStateOf(false)
    private var _lastInsertedNumber by mutableStateOf("")
    private var _expressionFontSize by mutableStateOf(72f)
    private var _resultFontSize by mutableStateOf(62f)
    private var _resultTextColor by mutableStateOf(CalculatorFontColor)

    var keval = Keval()
    var expression by mutableStateOf("") // 1 + 2 + 3 + 4
    var result by mutableStateOf("") // Result
    var expressionFontSize by mutableStateOf(_expressionFontSize)
    var resultFontSize by mutableStateOf(_resultFontSize)
    var resultTextColor by mutableStateOf(CalculatorFontColor)

    fun expressionTextHasOverFlow(){
        expressionFontSize *= 0.8f
    }

    fun resultTextHasOverFlow(){
        resultFontSize *= 0.8f
    }

    fun onButtonPressed(type: ButtonType, value: String){
        when(type){
            ButtonType.NUMBER -> {
                if(expression == "0"){
                    expression = value
                }
                else{
                    expression += value
                }
                _lastInsertedNumber += value
            }
            ButtonType.OPERATOR -> {
                addOperator(value)
            }
            ButtonType.DECIMAL_POINT -> {
                addDecimalPoint()
            }
            ButtonType.REMOVE -> {
                if(expression.isNotEmpty()){
                    val lastChar = expression.last()
                    println("Last: $lastChar")
                    if(lastChar == '.'){
                        _canInsertDecimalPoint = true
                        println("Yes")
                    }
                    expression = expression.dropLast(1)
                    _lastInsertedNumber.dropLast(1)
                }
            }
            ButtonType.EVALUATE -> {
                evaluateResult()
            }
            ButtonType.CLEAR -> {
                clearAll()
            }
        }
    }

    private fun addDecimalPoint(){
        if((_lastInsertedNumber.isNotEmpty() && ! _lastInsertedNumber.contains(".")) || _canInsertDecimalPoint){
            expression += "."
            _lastInsertedNumber = expression
            _canInsertDecimalPoint = false
        }
    }

    private fun addOperator(operator: String){
        _lastInsertedNumber = ""
        if(expression.isNotEmpty()){
            if(expression.last() != '.'){
                expression += operator
            }
        }
    }

    private fun evaluateResult(){
        result = try{
            var actualResult = keval.eval(expression.replace("x", "*"))
            if(hasDecimalValue(actualResult)){
                actualResult.toString()
            } else{
                actualResult.toInt().toString()
            }
        } catch(e: Exception){
            "Error!"
        }
        if(result == "Error!"){
            resultTextColor = Color.Red
        }
        else{
            resultTextColor = _resultTextColor
        }
        reset()
    }

    private fun hasDecimalValue(number: Double): Boolean{
        return number % 1.0 != 0.0
    }

    private fun reset(){
        clearExpression()
        expressionFontSize = _expressionFontSize
        resultFontSize = _resultFontSize
    }

    private fun clearExpression(){
        expression = ""
        _lastInsertedNumber = ""
        _canInsertDecimalPoint = true
    }

    private fun clearAll(){
        reset()
        result = ""
    }

    private fun hasOperator(): Boolean{
        return expression.contains("x").or(expression.contains("+")).or(expression.contains("-")).or(expression.contains("/")).or(expression.contains("%"))
    }
}