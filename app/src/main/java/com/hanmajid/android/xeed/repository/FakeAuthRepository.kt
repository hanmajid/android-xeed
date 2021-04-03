package com.hanmajid.android.xeed.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hanmajid.android.xeed.util.api.ApiResponse
import com.hanmajid.android.xeed.util.api.ApiSuccessResponse
import com.hanmajid.android.xeed.util.api.GenericApiResponseDetail
import com.hanmajid.android.xeed.data.api.login.LoginApiResponse
import com.hanmajid.android.xeed.util.AppExecutors
import com.hanmajid.android.xeed.util.Resource
import com.hanmajid.android.xeed.util.NetworkBoundResource
import javax.inject.Inject

class FakeAuthRepository @Inject constructor(
    private val appExecutors: AppExecutors,
) : AuthRepository {
    fun saveCallResultLogin(item: LoginApiResponse) {
        Thread.sleep(1000)
        item.data.payload?.let {
//            database.runInTransaction {
//                database.userDao().deleteAll()
//                database.userDao().insertAll(listOf(response.user))
//            }
//            with(sharedPreferences.edit()) {
//                putString(
//                    SP_KEY_USER_TOKEN,
//                    response.token,
//                )
//                putString(
//                    SP_KEY_USER_EMAIL,
//                    response.user.email,
//                )
//                commit()
//            }
        }
    }

    fun loadFromDbLogin(): LiveData<Boolean> {
        return MutableLiveData(false)
    }

    override fun login(shipId: String): LiveData<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean, LoginApiResponse>(appExecutors) {
            override fun saveCallResult(item: LoginApiResponse) {
                saveCallResultLogin(item)
            }

            override fun shouldFetch(data: Boolean?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<Boolean> {
                return loadFromDbLogin()
            }

            override fun createCall(): LiveData<ApiResponse<LoginApiResponse>> {
                return MutableLiveData(
                    ApiSuccessResponse(
                        body = LoginApiResponse(
                            data = GenericApiResponseDetail(
                                success = true,
                                payload = true,
                                message = null,
                                error = null,
                            ),
                            recordsTotal = 0,
                            recordsTotalExist = false,
                        ),
                        linkHeader = null,
                    )
                )
            }
        }.asLiveData()
    }
}