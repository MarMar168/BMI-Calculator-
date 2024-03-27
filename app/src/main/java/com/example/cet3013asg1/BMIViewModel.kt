package com.example.cet3013asg1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class BMIResult(val bmi: Double, val category: Any)
data class BmiCategory(val underweight: ClosedFloatingPointRange<Float>, val healthy: ClosedFloatingPointRange<Float>, val overweight: ClosedFloatingPointRange<Float>)

@Suppress("UNREACHABLE_CODE")
class BMIViewModel: ViewModel() {
    private val _bmiResult = MutableLiveData<BMIResult>()
    val bmiResult: MutableLiveData<BMIResult> get() = _bmiResult
    private val femalebmiCategories = mapOf(
        2 to BmiCategory(0.0f..14.3f, 14.4f..18.0f, 18.1f..19.1f),
        3 to BmiCategory(0.0f..12.9f, 13.0f..17.0f, 17.1f..18.3f),
        4 to BmiCategory(0.0f..13.6f, 13.7f..16.8f, 16.9f..18.0f ),
        5 to BmiCategory(0.0f..13.4f, 13.5f..16.8f, 16.9f..18.2f),
        6 to BmiCategory(0.0f..13.3f, 13.4f..17.1f, 17.2f..18.3f),
        7 to BmiCategory(0.0f..13.3f, 13.4f..17.6f, 17.7f..19.6f),
        8 to BmiCategory(0.0f..13.4f, 13.5f..18.3f, 18.4f..20.6f),
        9 to BmiCategory(0.0f..13.7f,13.8f..19.0f, 19.1f..21.8f),
        10 to BmiCategory(0.0f..13.9f, 14.0f..19.9f, 20.0f..22.9f),
        11 to BmiCategory(0.0f..14.3f, 14.4f..20.8f, 20.9f..24.1f),
        12 to BmiCategory(0.0f..14.7f, 14.8f..21.7f, 21.8f..25.2f),
        13 to BmiCategory(0.0f..15.2f, 15.3f.. 22.5f, 22.6f..26.2f),
        14 to BmiCategory(0.0f..15.7f, 15.8f..23.3f, 23.4f..27.2f),
        15 to BmiCategory(0.0f..16.2f, 16.3f..24.0f, 24.1f..28.1f),
        16 to BmiCategory(0.0f..16.7f, 16.8f..24.6f, 24.7f..28.9f),
        17 to BmiCategory(0.0f..17.1f, 17.2f..25.2f, 25.3f..29.6f),
        18 to BmiCategory(0.0f..17.5f, 17.6f..25.7f, 25.8f..30.3f),
        19 to BmiCategory(0.0f..17.7f, 17.8f..26.1f, 26.2f.. 31.0f),
        20 to BmiCategory(0.0f..17.7f, 17.8f..26.4f, 26.6f..31.8f)
    )
    private val malebmiCategories = mapOf(
        2 to BmiCategory(0.0f..14.7f, 14.8f..18.2f, 18.3f..19.4f),
        3 to BmiCategory(0.0f..14.2f, 14.3f..17.3f, 17.4f..18.3f),
        4 to BmiCategory(0.0f..13.9f, 14.0f..16.9f, 17.0f..17.8f),
        5 to BmiCategory(0.0f..13.8f, 13.9f..16.8f, 16.9f..17.9f),
        6 to BmiCategory(0.0f..13.6f, 13.7f..17.0f, 17.1f..18.4f),
        7 to BmiCategory(0.0f..13.6f, 13.7f..17.4f, 17.5f..19.1f),
        8 to BmiCategory(0.0f..13.7f, 13.8f..17.9f, 18.0f..19.5f),
        9 to BmiCategory(0.0f..13.9f, 14.0f..18.6f, 18.7f..21.0f),
        10 to BmiCategory(0.0f..14.1f, 14.2f..19.4f, 19.5f..22.1f),
        11 to BmiCategory(0.0f..14.4f, 14.5f..20.2f, 20.3f..23.2f),
        12 to BmiCategory(0.0f..14.9f, 15.0f..21.0f, 21.1f..24.2f),
        13 to BmiCategory(0.0f..15.3f, 15.4f..21.8f, 21.9f..25.1f),
        14 to BmiCategory(0.0f..15.9f, 16.0f..22.6f, 22.7f..26.0f),
        15 to BmiCategory(0.0f..16.4f, 16.5f..23.4f, 23.5f..26.8f),
        16 to BmiCategory(0.0f..17.0f, 17.1f..24.2f, 24.3f..27.5f),
        17 to BmiCategory(0.0f..17.5f, 17.6f..24.9f, 25.0f..28.2f),
        18 to BmiCategory(0.0f..18.1f, 18.2f..25.6f, 25.7f..28.9f),
        19 to BmiCategory(0.0f..18.6f, 18.7f..26.3f, 26.4f..29.7f),
        20 to BmiCategory(0.0f..19.0f, 19.1f..27.0f, 27.1f..30.6f)
    )

    fun calculateBMI(
        weight: Double,
        height: Double,
        isMetric: Boolean,
        age: Int,
        isGender: Boolean
    ) {
        val bmi = calculateRawBMI(weight, height, isMetric)
        val category = categorizeBMI(age, bmi, isGender)
        _bmiResult.value = BMIResult(bmi, category)
    }

    private fun calculateRawBMI(weight: Double, height: Double, isMetric: Boolean): Double {
        return if (isMetric) {
            weight / (height * height)
        } else {
            weight / (height * height) * 703
        }
    }
    private fun categorizeBMI(age: Int, bmi: Double, isGender: Boolean): String {
        if (age < 21) {
            val category = if (isGender) {
                malebmiCategories[age]
            } else {
                femalebmiCategories[age]
            }
            return when {
                category == null -> "Age not found"
                bmi in category.underweight -> "Underweight"
                bmi in category.healthy -> "Healthy Weight"
                bmi in category.overweight -> "Overweight"
                else -> "Obesity"
            }
        } else {
            return when {
                (bmi < 15) -> "Very severely underweight"
                (bmi in 16.0..18.5) -> "Severely underweight"
                (bmi in 18.5..25.0) -> "Underweight"
                (bmi in 25.0..30.0) -> "Normal (healthy weight)"
                (bmi in 30.0..35.0) -> "Mode Moderately "
                (bmi in 35.0..40.0) -> "Severely obese"
                else -> "Very severely obese"
            }
        }
    }
}
