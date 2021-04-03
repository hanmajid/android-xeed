package com.hanmajid.android.xeed.util.api

import android.util.Log
import retrofit2.Response
import java.util.regex.Pattern

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    body as GenericApiResponse<*>
                    if (body.data.success == true) {
                        ApiSuccessResponse(
                            body = body,
                            linkHeader = response.headers()["link"]
                        )
                    } else {
                        val bodyData = body.data
                        var errorMessage: String? = null
                        if (bodyData.error != null) {
                            errorMessage = bodyData.error
                        } else if (bodyData.message != null) {
                            errorMessage = bodyData.message
                        }
                        ApiErrorResponse(
                            errorMessage ?: "Unknown error"
                        )
                    }
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T,
    val links: Map<String, String>
) : ApiResponse<T>() {
    constructor(body: T, linkHeader: String?) : this(
        body = body,
        links = linkHeader?.extractLinks() ?: emptyMap()
    )

    @Suppress("unused")
    val nextPage: Int? by lazy(LazyThreadSafetyMode.NONE) {
        links[NEXT_LINK]?.let { next ->
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else {
                try {
                    Integer.parseInt(matcher.group(1)!!)
                } catch (ex: NumberFormatException) {
                    Log.d("ApiResponse", "cannot parse next page from %s")
                    null
                }
            }
        }
    }

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")

        @Suppress("SpellCheckingInspection")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)!!] = matcher.group(1)!!
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val error: String) : ApiResponse<T>()