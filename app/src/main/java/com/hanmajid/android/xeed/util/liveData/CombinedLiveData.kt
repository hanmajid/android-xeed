package com.hanmajid.android.xeed.util.liveData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * CombinedLiveData is a helper class to combine results from multiple LiveData sources.
 * @param liveDataList Variable number of LiveData arguments.
 * @param combine   Function reference that will be used to combine all LiveData data results.
 * @param R         The type of data returned after combining all LiveData data.
 * Usage:
 * CombinedLiveData<SomeType>(
 *     getLiveData1(),
 *     getLiveData2(),
 *     ... ,
 *     getLiveDataN()
 * ) { dataList: List<Any?> ->
 *     // Use dataList[0], dataList[1], ..., dataList[N] to return a SomeType value
 * }
 */
class CombinedLiveData<R>(
    vararg liveDataList: LiveData<*>,
    private val combine: (dataList: List<Any?>) -> R
) : MediatorLiveData<R>() {

    private val dataList: MutableList<Any?> = MutableList(liveDataList.size) { null }

    init {
        for (i in liveDataList.indices) {
            super.addSource(liveDataList[i]) {
                dataList[i] = it
                value = combine(dataList)
            }
        }
    }
}