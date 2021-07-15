package ua.org.kerzoll.giphytest.data.remote.elements

import javax.inject.Inject

class ElementRemoteSource @Inject constructor(
    private val elementService: ElementService
) : BaseDataSource() {
    suspend fun getSearch(apiKey: String, query: String, limit: Int, offset: Int) =
        getResult { elementService.search(apiKey, query, limit, offset, "g", "en") }
}