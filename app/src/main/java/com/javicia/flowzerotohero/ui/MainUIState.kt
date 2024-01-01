package com.javicia.flowzerotohero.ui

sealed class MainUIState {
    object Loading:MainUIState()
    data class Success(val numSucribers:Int):MainUIState()
    data class Error(val msg:String):MainUIState()
}