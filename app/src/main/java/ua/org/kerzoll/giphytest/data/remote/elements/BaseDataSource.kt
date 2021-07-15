package ua.org.kerzoll.giphytest.data.remote.elements

import android.util.Log
import retrofit2.Call
import ua.org.kerzoll.giphytest.util.Resource
import java.lang.Exception

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Call<T>): Resource<T> {
        try {
            val response = call().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    fun <T> error(message: String): Resource<T> {
        Log.d(TAG, message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

    companion object {
        private const val TAG = "BaseDataSource"
    }
}