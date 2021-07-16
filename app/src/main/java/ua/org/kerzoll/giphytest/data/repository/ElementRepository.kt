package ua.org.kerzoll.giphytest.data.repository

import ua.org.kerzoll.giphytest.data.remote.elements.ElementRemoteSource
import ua.org.kerzoll.giphytest.util.networkOperation
import javax.inject.Inject

class ElementRepository @Inject constructor(
    private val remoteDataSource: ElementRemoteSource
) {
    fun searchGif(query: String, limit: Int, offset: Int) = networkOperation(
        networkCall = {remoteDataSource.getSearch(query, limit, offset)}
    )
}