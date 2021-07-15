package ua.org.kerzoll.giphytest.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ua.org.kerzoll.giphytest.util.Resource.Status.*

private const val TAG = "DataAccess"

fun <A> networkOperation(networkCall: suspend () -> Resource<A>): LiveData<Resource<A>> =
    liveData(Dispatchers.IO){
        Log.d(TAG, "Start networkOperation")
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()
        if(responseStatus.status == SUCCESS) {
            emit(responseStatus)
        }else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }
