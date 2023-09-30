package com.gramzin.regionsapp.navigation

import com.gramzin.regionsapp.domain.model.Region

sealed class Screen(val route: String){

    object Main: Screen(ROUTE_MAIN)

    object RegionDetails: Screen(ROUTE_REGION_DETAILS){

        const val ROUTE_BASE = "region"
        const val KEY_PARAM_ID = "id"

        fun getRouteWithParam(region: Region): String {
            return "$ROUTE_BASE/${region.id}"
        }
    }

    companion object{
        const val ROUTE_MAIN = "main"
        const val ROUTE_REGION_DETAILS = "${RegionDetails.ROUTE_BASE}/{${RegionDetails.KEY_PARAM_ID}}"
    }
}
