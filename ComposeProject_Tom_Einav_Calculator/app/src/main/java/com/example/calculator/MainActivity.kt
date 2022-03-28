package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.calculator.ui.theme.*

private val calculatorResult = MutableLiveData<Double>().apply { value = 0.toDouble() }
private val calculatorExpression = MutableLiveData<String>("1+2+3+4")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val calculatorViewModel by viewModels<CalculatorViewModel>()

        setContent {
            window.statusBarColor = CalculatorResultViewBackgroundColor.toArgb()
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Column {
                    Calculator(calculatorViewModel = calculatorViewModel)
                }
            }
        }
    }
}

@Composable
fun RowScope.CalculatorButton(text: String, color: Color, fontColor: Color = CalculatorFontColor, type: ButtonType = ButtonType.NUMBER, weight: Float = 1f, calculatorViewModel: CalculatorViewModel){
    var context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    MaterialTheme {
        Button(
            onClick = {
                calculatorViewModel.onButtonPressed(type, text);
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                      },
            modifier = Modifier
                .size(90.dp)
                .padding(5.dp)
                .weight(weight),
            shape =  RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
        ) {
            Text(text = text, fontSize = 32.sp, fontFamily = FontFamily.Monospace, color = fontColor, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CalculatorDeleteButton(color: Color, calculatorViewModel: CalculatorViewModel){
    var haptic = LocalHapticFeedback.current
    MaterialTheme {
        Button(
            onClick = {
                calculatorViewModel.onButtonPressed(ButtonType.REMOVE, "Remove");
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                      },
            modifier = Modifier
                .size(90.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = color)) {
            Icon(
                Icons.Outlined.ArrowBack,
                contentDescription = "Delete",
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun CalculatorBody(calculatorViewModel: CalculatorViewModel = CalculatorViewModel()){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            CalculatorButton(text = "AC", color = CalculatorClearButtonColor, type = ButtonType.CLEAR, fontColor = Color.Black, calculatorViewModel = calculatorViewModel, weight = 2f)
            CalculatorButton(text = "%", color = CalculatorOperatorButtonColor, type = ButtonType.OPERATOR, fontColor = Color.Black, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "/", color = CalculatorOperatorButtonColor, type = ButtonType.OPERATOR, fontColor = Color.Black, calculatorViewModel = calculatorViewModel)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            CalculatorButton(text = "7", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "8", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "9", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "x", color = CalculatorOperatorButtonColor, fontColor = Color.Black, type = ButtonType.OPERATOR, calculatorViewModel = calculatorViewModel)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            CalculatorButton(text = "4", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "5", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "6", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "-", color = CalculatorOperatorButtonColor, fontColor = Color.Black, type = ButtonType.OPERATOR, calculatorViewModel = calculatorViewModel)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            CalculatorButton(text = "1", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "2", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "3", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "+", color = CalculatorOperatorButtonColor, fontColor = Color.Black, type = ButtonType.OPERATOR, calculatorViewModel = calculatorViewModel)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            CalculatorButton(text = "0", color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = ".", color = CalculatorButtonColor, type = ButtonType.DECIMAL_POINT, calculatorViewModel = calculatorViewModel)
            CalculatorDeleteButton(color = CalculatorButtonColor, calculatorViewModel = calculatorViewModel)
            CalculatorButton(text = "=", color = CalculatorOperatorButtonColor, fontColor = Color.Black, type = ButtonType.EVALUATE, calculatorViewModel = calculatorViewModel)
        }
    }
}

@Composable
fun Calculator(calculatorViewModel: CalculatorViewModel = CalculatorViewModel()){
    Column {
        // Calculator Result View
        Box(
            modifier = Modifier
                .weight(3f)
                .background(CalculatorResultViewBackgroundColor)
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.CenterEnd
        ){
             ResultView(calculatorViewModel)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(7f)
                .background(CalculatorBodyColor)
                .padding(10.dp)
        ){
             CalculatorBody(calculatorViewModel)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun CalculatorPreview(){
    Calculator()
}

@Composable
fun ResultView(calculatorViewModel: CalculatorViewModel = CalculatorViewModel()){
    var expressionFontSize = calculatorViewModel.expressionFontSize
    var resultFontSize = calculatorViewModel.resultFontSize
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
        ){
            SelectionContainer() {
                Text(
                    text = calculatorViewModel.expression,
                    color = CalculatorFontColor,
                    fontFamily = FontFamily.Monospace,
                    fontSize = expressionFontSize.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onTextLayout = {
                        if (it.hasVisualOverflow) {
                            calculatorViewModel.expressionTextHasOverFlow() // you can tune this constant
                        }
                    },
                    textAlign = TextAlign.End,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f),
            contentAlignment = Alignment.BottomEnd
        ){
            SelectionContainer() {
                Text(
                    text = calculatorViewModel.result.format(8),
                    color = calculatorViewModel.resultTextColor,
                    fontFamily = FontFamily.Monospace,
                    fontSize = resultFontSize.sp,
                    onTextLayout = {
                        if (it.hasVisualOverflow) {
                            calculatorViewModel.resultTextHasOverFlow() // you can tune this constant
                        }
                    },
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
@Preview
fun CalculatorBodyPreview() {
    CalculatorBody()
}
