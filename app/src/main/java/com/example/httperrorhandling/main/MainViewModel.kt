package com.example.httperrorhandling.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httperrorhandling.network.errors.ErrorHandler
import com.example.httperrorhandling.network.repository.TestingErrorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TestingErrorRepository,
    private val errorHandler: ErrorHandler,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScreenState())
    val uiState = _uiState.asStateFlow()

    fun testUserInfo() {
        viewModelScope.launch {
            repository.getUser()
                .flowOn(Dispatchers.IO)
                .catch { errorHandler.handleError(it) }
                .collectLatest { user ->
                    _uiState.update {
                        it.copy(user = user)
                    }
                }
        }
    }
}