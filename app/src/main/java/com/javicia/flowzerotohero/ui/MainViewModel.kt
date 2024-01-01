package com.javicia.flowzerotohero.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javicia.flowzerotohero.data.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val mainRepository = MainRepository()

    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading)
    val uiState: StateFlow<MainUIState> = _uiState

    fun example() {
        viewModelScope.launch {
            mainRepository.count
                .map { it.toString() }//map tranforma el resultado
                .collect {
                    Log.i("Javicia", it)
                }
        }

        fun save(info: String) {

        }

        fun example2() {
            viewModelScope.launch {
                mainRepository.count
                    .map { it.toString() }//map tranforma el resultado
                    .onEach { save(it) }//permite realizar acciones secundarias como guardar en base de datos
                    .catch { error ->
                        Log.i("Javicia", "Error ${error.message}")
                    }
                    .collect {
                        Log.i("Javicia", it)
                    }
            }
        }
    }
    fun example3(){
    viewModelScope.launch {
        mainRepository.count
            .catch { _uiState.value=MainUIState.Error(it.message.orEmpty()) }
            .flowOn(Dispatchers.IO)//Hilo secundario
            .collect{
                _uiState.value=MainUIState.Success(it)
            }
    }
    }
}