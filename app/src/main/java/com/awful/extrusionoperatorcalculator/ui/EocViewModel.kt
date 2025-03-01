package com.awful.extrusionoperatorcalculator.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.awful.extrusionoperatorcalculator.data.DataSource.fractionMap
import kotlin.time.Duration.Companion.minutes

class EocViewModel : ViewModel() {
    var currentPullerSpeed: String by mutableStateOf("2.5")
        private set
    fun setCPS(value: String) {
        currentPullerSpeed = value
        isErrorCPS = value.toDoubleOrNull() == null
    }

    var isErrorCPS: Boolean by mutableStateOf(false)
        private set

    var currentLength: String by mutableStateOf("252")
        private set
    fun setCL(value: String) {
        currentLength = value
        isErrorCL = value.toIntOrNull() == null
    }

    var isErrorCL: Boolean by mutableStateOf(false)
        private set

    var currentFraction: String by mutableStateOf("0")
        private set
    fun setCF(value: String) { currentFraction = value }

    var desiredLength: String by mutableStateOf("252")
        private set
    fun setDL(value: String) {
        desiredLength = value
        isErrorDL = value.toIntOrNull() == null
    }

    var isErrorDL: Boolean by mutableStateOf(false)
        private set

    var desiredFraction: String by mutableStateOf("0")
        private set
    fun setDF(value: String) { desiredFraction = value }

    var piecesPerRack: String by mutableStateOf("60")
        private set
    fun setPPR(value:String) {
        piecesPerRack = value
        isErrorPPR = piecesPerRack.toIntOrNull() == null
    }

    var isErrorPPR: Boolean  by mutableStateOf(false)

    var currentSetting: String by mutableStateOf("252")
        private set
    fun setCS(value: String) {
        currentSetting = value
        isErrorCS = value.toIntOrNull() == null
    }

    var isErrorCS: Boolean by mutableStateOf(false)
        private set

    var currentFeederSpeed: String by mutableStateOf("30.5")
        private set
    fun setCFS(value: String) {
        currentFeederSpeed = value
        isErrorCFS = value.toDoubleOrNull() == null
    }

    var isErrorCFS: Boolean by mutableStateOf(false)
        private set

    var currentExtruderSpeed: String by mutableStateOf("30.5")
        private set
    fun setCES(value: String) {
        currentExtruderSpeed = value
        isErrorCES = value.toDoubleOrNull() == null
    }

    var isErrorCES: Boolean by mutableStateOf(false)
        private set

    var targetPullerSpeed: String by mutableStateOf("2.5")
        private set
    fun setTPS(value: String) {
        targetPullerSpeed = value
        isErrorTPS = value.toDoubleOrNull() == null
    }

    var isErrorTPS: Boolean by mutableStateOf(false)
        private set

    var spoolLength: String by mutableStateOf("30.5")
        private set
    fun setSL(value: String) {
        spoolLength = value
        isErrorSL = value.toIntOrNull() == null
    }

    var isErrorSL: Boolean by mutableStateOf(false)
        private set

    var rackTime: String by mutableStateOf("0:00 (h:mm)")
        private set
    @SuppressLint("DefaultLocale")
    fun calculateRackTime() {
        val minutesPerRack = (
            piecesPerRack.toDouble()
            * (currentLength.toDouble() + fractionMap[currentFraction]!!)
            * .0254
            / currentPullerSpeed.toDouble()
        ).minutes
        rackTime = minutesPerRack.toComponents {
            hours, minutes, _, _ -> "$hours:" + String.format("%02d", minutes) + " (h:mm)"
        }
    }

    var sawSetting: String by mutableStateOf("252")
        private set
    @SuppressLint("DefaultLocale")
    fun calculateSawSetting() {
        sawSetting = String.format(
            "%.3f",
            currentSetting.toDouble()
                * (desiredLength.toDouble() + fractionMap[desiredFraction]!!)
                / (currentLength.toDouble() + fractionMap[currentFraction]!!)
        )
    }

    var newFeederSetting:String by mutableStateOf("")
        private set
    var newExtruderSetting: String by mutableStateOf("")
        private set
    @SuppressLint("DefaultLocale")
    fun calculateSpeedSettings() {
        newFeederSetting = String.format(
            "%.2f",
            targetPullerSpeed.toDouble()
                * currentFeederSpeed.toDouble()
                / currentPullerSpeed.toDouble()
        )
        newExtruderSetting = String.format(
            "%.2f",
            targetPullerSpeed.toDouble()
                * currentExtruderSpeed.toDouble()
                / currentPullerSpeed.toDouble()
        )
    }

    var spoolTime: String by mutableStateOf("")
        private  set
    @SuppressLint("DefaultLocale")
    fun calculateSpoolTime() {
        val minutesPerSpool = (spoolLength.toInt() * .3048 / currentPullerSpeed.toDouble()).minutes
        spoolTime = minutesPerSpool.toComponents {
                hours, minutes, _, _ -> "$hours:" + String.format("%02d", minutes) + " (h:mm)"
        }
    }
}