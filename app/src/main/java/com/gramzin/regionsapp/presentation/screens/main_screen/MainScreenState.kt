package com.gramzin.regionsapp.presentation.screens.main_screen

import com.gramzin.regionsapp.domain.model.Region

sealed interface MainScreenState{

    object Initial: MainScreenState

    object Loading: MainScreenState

    class Data(val regions: List<Region>, val isRefresh: Boolean): MainScreenState

    object Error: MainScreenState
}
