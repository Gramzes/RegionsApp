package com.gramzin.regionsapp.presentation.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gramzin.regionsapp.domain.model.Region
import com.gramzin.regionsapp.domain.usecase.ChangeLikeStateRegionUseCase
import com.gramzin.regionsapp.domain.usecase.GetRegionsUseCase
import com.gramzin.regionsapp.domain.usecase.RefreshRegionsUseCase
import com.gramzin.regionsapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val changeLikeStateUseCase: ChangeLikeStateRegionUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val refreshRegionsUseCase: RefreshRegionsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainScreenState.Initial as MainScreenState)
    val state = _state.asStateFlow()

    init {

        viewModelScope.launch {
            getRegionsUseCase()
                .onStart { _state.value = MainScreenState.Loading }
                .collect {
                    _state.value = MainScreenState.Data(regions = it, isRefresh = false)
                }

        }
    }

    fun refreshRegions(){
        viewModelScope.launch {
            when(val currentState = state.value){
                is MainScreenState.Data -> {
                    _state.value = MainScreenState.Data(
                        regions = currentState.regions,
                        isRefresh = true
                    )
                    refreshRegionsUseCase()
                }
                else -> {}
            }
        }
    }

    fun changeLikeState(region: Region){
        viewModelScope.launch {
            changeLikeStateUseCase(region)
        }
    }
}