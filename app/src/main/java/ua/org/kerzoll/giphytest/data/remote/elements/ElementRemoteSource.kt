package ua.org.kerzoll.giphytest.data.remote.elements

import javax.inject.Inject

class ElementRemoteSource @Inject constructor(
    private val elementService: ElementService
) : BaseDataSource() {

    private val apiKey = "YGHnKKBGSydS6nSt6WAoUcICWwmgCfvL";

    suspend fun getSearch(query: String, limit: Int, offset: Int) =
        getResult { elementService.searchGif(apiKey, query, limit, offset, "g", "en") }
}