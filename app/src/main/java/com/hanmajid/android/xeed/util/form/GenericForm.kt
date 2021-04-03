package com.hanmajid.android.xeed.util.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hanmajid.android.xeed.util.Resource
import com.hanmajid.android.xeed.util.ResourceStatus
import com.hanmajid.android.xeed.util.liveData.AbsentLiveData

class GenericForm<TBody, TResult>(
    private val formFields: List<GenericFormField>,
    private val onPrepareBody: (values: List<String>) -> TBody,
    private val onSubmit: (values: TBody) -> LiveData<Resource<TResult>>,
) {
    fun trySubmit() {
        // For each form fields:
        formFields.forEach {
            if (it.value.value == null) {
                // Touch the field if it's not touched yet.
                it.touchSync()
                return@trySubmit
            } else if (it.isError.value == true) {
                // Returns if the field still has errors.
                return@trySubmit
            }
        }
        // If all fields are touched & valid, submit form.
        val values = formFields.map {
            it.value.value ?: ""
        }
        apiBody.postValue(onPrepareBody(values))
    }

    fun resetApiBody() {
        apiBody.postValue(null)
    }

    val apiBody = MutableLiveData<TBody?>(null)
    val resultResource: LiveData<Resource<TResult>> = Transformations.switchMap(apiBody) {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            onSubmit(it)
        }
    }
    val isLoading = Transformations.map(resultResource) {
        it?.success == ResourceStatus.LOADING
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "GenericForm"
    }
}