package com.andrewgroe.tiptree.viewmodel

import android.app.Application
import com.andrewgroe.tiptree.R
import com.andrewgroe.tiptree.model.Calculator
import com.andrewgroe.tiptree.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(
        app: Application, private val calculator: Calculator = Calculator()) : ObservableViewModel(app) {

    var inputCheckAmount = ""
    var inputTipPercentage = ""
    var inputSplit = 1

    var outputCheckAmount = ""
    var outputTipAmount = ""
    var outputTotalDollarAmount = ""

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation) {
        outputCheckAmount = getApplication<Application>().getString(R.string.dollar_amount, tc.checkAmount)
        outputTipAmount = getApplication<Application>().getString(R.string.dollar_amount, tc.tipAmount)
        outputTotalDollarAmount = getApplication<Application>().getString(R.string.dollar_amount, tc.grandTotal)
    }

    fun calculateTip() {
        // Check if bill is being split
        val checkAmount: Double? = if (inputSplit > 1) {
            // Convert from String
            inputCheckAmount.toDoubleOrNull()?.div(inputSplit.toDouble())
        } else {
            // Convert from String
            inputCheckAmount.toDoubleOrNull()
        }

        // Remove percent symbol
        if (inputTipPercentage.contains("%")) {
            inputTipPercentage = inputTipPercentage.replace("%", "")
        }
        // Convert from String
        val tipPct = inputTipPercentage.toIntOrNull()

        // Call update outputs

        if (checkAmount != null && tipPct != null) {
            // Log.d(TAG, "CheckAmount: $checkAmount, TipPercentage: $tipPct")
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
            notifyChange()
        } else if (checkAmount == null) {
            updateOutputs(calculator.calculateTip(0.0, tipPct!!))
            notifyChange()
        }


    }
}