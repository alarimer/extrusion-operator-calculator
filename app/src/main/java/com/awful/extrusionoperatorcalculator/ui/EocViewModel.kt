package com.awful.extrusionoperatorcalculator.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.awful.extrusionoperatorcalculator.data.DataSource.fractionMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.minutes

class EocViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EocUiState())
    val uiState: StateFlow<EocUiState> = _uiState.asStateFlow()

    @SuppressLint("DefaultLocale")
    fun calculateRackTime() {
        val minutesPerRack = (
            _uiState.value.piecesPerRack.toDouble() *
            (_uiState.value.currentLength.toDouble() + fractionMap[_uiState.value.currentFraction]!!)
            * .0254
            / _uiState.value.currentPullerSpeed.toDouble()
        ).minutes
        val formattedTime = minutesPerRack.toComponents {
            hours, minutes, _, _ -> "$hours:" + String.format("%02d", minutes) + " (h:mm)"
        }
        _uiState.update { currentState -> currentState.copy(rackTime = formattedTime) }
    }
}