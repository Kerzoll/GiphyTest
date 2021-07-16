package ua.org.kerzoll.giphytest.data.remote.elements

import retrofit2.Call
import retrofit2.http.*
import ua.org.kerzoll.giphytest.data.entites.elements.ElementEntityRemote

interface ElementService {

    @GET("gifs/search")
    fun searchGif(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("lang") lang: String,
    ): Call<ElementEntityRemote>

}