package com.hanmajid.android.xeed.util.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class GenericFormField(
    val id: String,
    validator: (value: String?) -> String?,
) {
    val value = MutableLiveData<String?>(null)

    val error: LiveData<String?> = Transformations.map(value, validator)

    val isError = Transformations.map(error) {
        it != null
    }

    fun touchSync() {
        if (value.value == null) {
            value.value = ""
        }
    }
}