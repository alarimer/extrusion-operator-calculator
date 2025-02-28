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

    var piecesPerRack: String by mutableStateOf("60")
        private set
    fun setPPR(value:String) {
        piecesPerRack = value
        isErrorPPR = piecesPerRack.toIntOrNull() == null
    }

    var isErrorPPR: Boolean  by mutableStateOf(false)

    var rackTime: String by mutableStateOf("0:00 (h:mm)")
        private set
    @SuppressLint("DefaultLocale")
    fun calculateRackTime() {
        val minutesPerRack = (
            piecesPerRack.toDouble() *
            (currentLength.toDouble() + fractionMap[currentFraction]!!)
            * .0254
            / currentPullerSpeed.toDouble()
        ).minutes
        rackTime = minutesPerRack.toComponents {
            hours, minutes, _, _ -> "$hours:" + String.format("%02d", minutes) + " (h:mm)"
        }
    }
}