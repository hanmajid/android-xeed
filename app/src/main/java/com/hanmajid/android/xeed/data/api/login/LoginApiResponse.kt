package com.hanmajid.android.xeed.data.api.login

import com.hanmajid.android.xeed.util.api.GenericApiResponse
import com.hanmajid.android.xeed.util.api.GenericApiResponseDetail

class LoginApiResponse(
    override val data: GenericApiResponseDetail<Boolean>,
    override val recordsTotal: Int,
    override val recordsTotalExist: Boolean
) : GenericApiResponse<Boolean>