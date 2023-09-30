package com.gramzin.regionsapp.presentation.screens.details_screen

import com.gramzin.regionsapp.domain.model.Region

sealed interface DetailsScreenState{

    object Initial: DetailsScreenState

    object Loading: DetailsScreenState

    class Data(val region: Region): DetailsScreenState

    object Error: DetailsScreenState
}
