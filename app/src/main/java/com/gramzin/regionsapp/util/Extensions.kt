package com.gramzin.regionsapp.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.mergeWith(flow: Flow<T>) = merge(this, flow)