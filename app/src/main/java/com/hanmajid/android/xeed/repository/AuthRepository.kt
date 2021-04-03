package com.hanmajid.android.xeed.repository

import androidx.lifecycle.LiveData
import com.hanmajid.android.xeed.util.Resource

interface AuthRepository {
    fun login(shipId: String): LiveData<Resource<Boolean>>
}