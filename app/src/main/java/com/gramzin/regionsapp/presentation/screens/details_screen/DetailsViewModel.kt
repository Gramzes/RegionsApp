package com.gramzin.regionsapp.presentation.screens.details_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gramzin.regionsapp.domain.model.Region
import com.gramzin.regionsapp.domain.usecase.ChangeLikeStateRegionUseCase
import com.gramzin.regionsapp.domain.usecase.GetRegionByIdUseCase
import com.gramzin.regionsapp.domain.usecase.GetRegionsUseCase
import com.gramzin.regionsapp.domain.usecase.RefreshRegionsUseCase
import com.gramzin.regionsapp.navigation.Screen
import com.gramzin.regionsapp.presentation.screens.main_screen.MainScreenState
import com.gramzin.regionsapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val changeLikeStateUseCase: ChangeLikeStateRegionUseCase,
    savedStateHandle: SavedStateHandle,
    getRegionByIdUseCase: GetRegionByIdUseCase
): ViewModel() {

    private val regionId: String = checkNotNull(savedStateHandle[Screen.RegionDetails.KEY_PARAM_ID])
    val state = getRegionByIdUseCase(regionId)
        .map {
            DetailsScreenState.Data(it) as DetailsScreenState
        }.onStart {
           emit(DetailsScreenState.Loading)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, DetailsScreenState.Initial)

    fun changeLikeState(region: Region){
        viewModelScope.launch {
            changeLikeStateUseCase(region)
        }
    }
}