package com.example.cet3013asg1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cet3013asg1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityMainBinding
        lateinit var viewModel: BMIViewModel
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup the toolbar
        setSupportActionBar(binding.toolbar)

        //create view model object
        viewModel = ViewModelProvider(this).get(BMIViewModel::class.java)

        fun calculateBMI() {
            //get user weight, height,
            val weight = binding.textWeight.text.toString().toDouble()
            val height = binding.textHeight.text.toString().toDouble()
            val isMetric = binding.unitRadioGroup.checkedRadioButtonId == R.id.button_metric
            val age = binding.textAge.text.toString().toInt()
            val isGender = binding.genderRadioGroup.checkedRadioButtonId == R.id.button_male

            viewModel.calculateBMI(weight, height, isMetric, age, isGender)
        }
        binding.calculateButton.setOnClickListener {
            val weightText = binding.textWeight.text.toString()
            val heightText = binding.textHeight.text.toString()
            val ageText = binding.textAge.text.toString()
            val unitRadioGroup: RadioGroup = findViewById(R.id.unitRadioGroup)
            val genderRadioGroup: RadioGroup = findViewById(R.id.genderRadioGroup)

            if (weightText.isEmpty() || heightText.isEmpty() || ageText.isEmpty()) {
                // Display an error message or highlight the empty fields
                Toast.makeText(this, "Please enter all required information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (unitRadioGroup.checkedRadioButtonId == -1) {
                // Display an error message indicating that the user should select a unit
                Toast.makeText(this, "Please select a unit (Imperial/Metric)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (genderRadioGroup.checkedRadioButtonId == -1) {
                // Display an error message indicating that the user should select a gender
                Toast.makeText(this, "Please select a unit (Male/ Female)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(ageText.toInt() < 2)
            {
                // Display an error message indicating that the user should select a gender
                Toast.makeText(this, "Age cannot be less than 2", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(ageText.toInt() > 100)
            {
                // Display an error message indicating that the user should select a gender
                Toast.makeText(this, "Age cannot be more than 100", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            calculateBMI()

        }
        binding.buttonClear?.setOnClickListener {
            // Clear EditText fields
            binding.textWeight.text.clear()
            binding.textHeight.text.clear()
            binding.textAge.text.clear()

            // Clear radio buttons in RadioGroups
            binding.unitRadioGroup.clearCheck()
            binding.genderRadioGroup.clearCheck()

            // Clear the result text
            binding.textResult.text = ""
        }
        //set up for radiogroup button
        val unitRadioGroup: RadioGroup = findViewById(R.id.unitRadioGroup)
        val heightEditText: EditText = findViewById(R.id.text_height)
        val weightEditText: EditText = findViewById(R.id.text_weight)
        unitRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.button_imperial -> {
                    heightEditText.hint = "Height (inches)"
                    weightEditText.hint = "Weight (pounds)"
                }
                R.id.button_metric -> {
                    heightEditText.hint = "Height (m)"
                    weightEditText.hint = "Weight (kg)"
                }
            }
        }
        viewModel.bmiResult.observe(this, Observer { result ->
            val formattedResult = getString(R.string.bmi_result_with_status, result.bmi, result.category)
            binding.textResult.text = formattedResult
        })
    }
}


