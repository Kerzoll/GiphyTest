package ua.org.kerzoll.giphytest.data.entites.elements

data class ElementEntityRemote(
    val data: List<ElementGiphy>,
    val pagination: PaginationGiphy,
    val meta: MetaGiphy
)

data class ElementGiphy(
    val id: String?,
    val url: String?,
    val embed_url: String?,
    val title: String?
)

data class PaginationGiphy(
    val totalCount: String?,
    val count: String?,
    val offset: String
)

data class MetaGiphy(
    val status: String?,
    val msg: String?,
    val response_id: String?
)