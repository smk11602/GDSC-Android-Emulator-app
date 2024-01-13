package com.myfirstandroidapp.emulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.myfirstandroidapp.emulator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val firstNumberText = StringBuilder("") //값이 많이 변경되는 경우
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#,###") // 세자리마다 , 찍기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun numberClicked(view : View) {
        val numberString = (view as? Button)?.text?.toString() ?: "" //button이 아니면 ""가 나오게
        val numberText = if(operatorText.isEmpty()) firstNumberText else secondNumberText

        numberText.append(numberString)
        updateEquationTextView()
    }

    fun clearClicked(view : View) {
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()

        updateEquationTextView()
        binding.resultTextView.text = ""
    }

    fun equalClicked(view : View) {

        if(firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this, "올바르지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val firstNumber = firstNumberText.toString().toBigDecimal()  //아주 큰 숫자를 받았을 때 오류 없이 처리하기
        val secondNumber = secondNumberText.toString().toBigDecimal()

        val result = when(operatorText.toString()) {
            "+" -> decimalFormat.format(firstNumber + secondNumber)
            "-" -> decimalFormat.format(firstNumber - secondNumber) //format()해서 나온 것은 값이 문자열임
            else -> "잘못된 수식입니다."
        }

        binding.resultTextView.text = result
    }

    fun operatorClicked(view : View) {
        val operatorString = (view as? Button)?.text?.toString() ?: "" //button이 아니면 ""가 나오게

        if(firstNumberText.isEmpty()) {
            Toast.makeText(this, "숫자를 먼저 입력하세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if(secondNumberText.isNotEmpty()) {
            Toast.makeText(this, "1개의 연산자에 대해서만 연산이 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        operatorText.append(operatorString) // date update
        updateEquationTextView() //update UI
    }
    private fun updateEquationTextView() { //decimalFormat()을 통해 알아서 0이 맨 앞에 들어가고 다른 숫자가 입력되면 0이 사라지게
        val firstFormattedNumber = if(firstNumberText.isNotEmpty()) decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if(secondNumberText.isNotEmpty()) decimalFormat.format(secondNumberText.toString().toBigDecimal()) else ""
        binding.equationTextView.text = "$firstFormattedNumber $operatorText $secondFormattedNumber"
    }

}