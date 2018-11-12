package com.andrewgroe.tiptree.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.andrewgroe.tiptree.utils.DecimalDigitsInputFilter
import com.andrewgroe.tiptree.R
import com.andrewgroe.tiptree.databinding.ActivityTipCalculatorBinding
import com.andrewgroe.tiptree.viewmodel.CalculatorViewModel
import com.ramotion.fluidslider.FluidSlider
import kotlinx.android.synthetic.main.content_tip_calculator.*


class TipCalculatorActivity : AppCompatActivity(), TextWatcher {

    lateinit var binding: ActivityTipCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tip_calculator)

        binding.vm = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        setSupportActionBar(binding.toolbar)

        // Limit text input to 2 decimal places
        input_check_amount.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(5, 2))
        // Set listener on EditText
        input_check_amount.addTextChangedListener(this)
        sliderHandler()
        splitNumberPickerListener()

    }

    // Listens for changes in order to split check
    private fun splitNumberPickerListener() {
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


    // Handles tip percentage slider setup and monitoring
    private fun sliderHandler() {
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


    /*
    Text Watcher Implementation Methods
    */
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        binding.vm?.calculateTip()
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
