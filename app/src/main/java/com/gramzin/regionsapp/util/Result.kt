package com.gramzin.regionsapp.util

sealed class Result <out T>(val data: T?) {

    data class Success<out R>(private val _data: R): Result<R>(_data)

    data class Loading<out R>(private val _data: R): Result<R>(_data)

    data class Error(val exception: Throwable): Result<Nothing>(null)
}