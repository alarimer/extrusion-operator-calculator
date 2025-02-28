package com.awful.extrusionoperatorcalculator.ui

data class EocUiState(
    var currentPullerSpeed: String = "2.5",
    var isErrorCPS: Boolean = false,
    var currentLength: String = "252",
    var isErrorCL: Boolean = false,
    var currentFraction: String = "0",
    var piecesPerRack: String = "60",
    var isErrorPPR: Boolean  = false,
    var rackTime: String = "0:00 (h:mm)"
)
