package com.hanmajid.android.xeed.util.api

interface GenericApiResponse<T> {
    val data: GenericApiResponseDetail<T>
    val recordsTotal: Int
    val recordsTotalExist: Boolean
}

data class GenericApiResponseDetail<T>(
    val success: Boolean?,
    val payload: T?,
    val error: String?,
    val message: String? // Used when token is invalid
)