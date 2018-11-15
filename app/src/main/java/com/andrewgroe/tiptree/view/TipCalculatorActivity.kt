package com.andrewgroe.tiptree.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.andrewgroe.tiptree.R
import com.andrewgroe.tiptree.databinding.ActivityTipCalculatorBinding
import com.andrewgroe.tiptree.utils.afterTextChanged
import com.andrewgroe.tiptree.utils.makeClearableEditText
import com.andrewgroe.tiptree.utils.onRightDrawableClicked
import com.andrewgroe.tiptree.utils.setInputFilter
import com.andrewgroe.tiptree.viewmodel.CalculatorViewModel
import com.ramotion.fluidslider.FluidSlider
import kotlinx.android.synthetic.main.content_tip_calculator.*


class TipCalculatorActivity : AppCompatActivity() {

    lateinit var binding: ActivityTipCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tip_calculator)

        binding.vm = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        setSupportActionBar(binding.toolbar)

        initEditText()
        initSlider()
        initNumberPicker()

    }

    private fun initEditText() {
        val input = input_check_amount

        // Limit text input decimal places
        setInputFilter(input)

        // Listen for changes in EditText
        input.afterTextChanged { binding.vm?.calculateTip() }

        // Add cancel button to EditText
        addRightCancelDrawable(input)
        // Cancel button onClick
        input.onRightDrawableClicked { it.text.clear() }
        // Handle cancel button visibility
        input.makeClearableEditText(null, null)
    }

    // Number Picker for splitting check
    private fun initNumberPicker() {
        number_picker.setOnValueChangeListener { view, oldValue, newValue ->
            // Send new value to ViewModel
            binding.vm!!.inputSplit = newValue
            binding.vm!!.calculateTip()

            // Check if bill is being split
            if (newValue > 1) {
                // Show "per person" view and divider
                sub_total_div.visibility = View.VISIBLE
                sub_total_person.visibility = View.VISIBLE
                tip_div.visibility = View.VISIBLE
                tip_person.visibility = View.VISIBLE
                total_div.visibility = View.VISIBLE
                total_person.visibility = View.VISIBLE
            } else {
                // Hide "per person" view and divider
                sub_total_div.visibility = View.INVISIBLE
                sub_total_person.visibility = View.INVISIBLE
                tip_div.visibility = View.INVISIBLE
                tip_person.visibility = View.INVISIBLE
                total_div.visibility = View.INVISIBLE
                total_person.visibility = View.INVISIBLE
            }
        }
    }

    // Tip percentage slider setup and monitoring
    private fun initSlider() {
        // Setup tip percentage slider
        val max = 50
        val min = 5
        val total = max - min
        // Handle events slider events
        val slider = findViewById<FluidSlider>(R.id.slider_tip_percentage)
        slider.positionListener = { pos ->
            // Update bubble text
            slider.bubbleText = "${min + (total * pos).toInt()}%"
            // Send tip % to ViewModel
            binding.vm!!.inputTipPercentage = slider.bubbleText.orEmpty()
            // Perform Calculation: allows for calculation to update in real time as user changes %
            binding.vm?.calculateTip()
        }

        slider.position = 0.3f
        slider.startText = "$min%"
        slider.endText = "$max%"
    }

    // Adds cancel button in EditText
    private fun addRightCancelDrawable(editText: EditText) {
        val cancel = ContextCompat.getDrawable(this, R.drawable.ic_cancel_red_24dp)
        cancel?.setBounds(0, 0, cancel.intrinsicWidth, cancel.intrinsicHeight)
        editText.setCompoundDrawables(null, null, cancel, null)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about -> {

                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
