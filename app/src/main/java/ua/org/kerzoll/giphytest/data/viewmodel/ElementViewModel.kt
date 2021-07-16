package ua.org.kerzoll.giphytest.data.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.org.kerzoll.giphytest.data.entites.elements.ElementEntityRemote
import ua.org.kerzoll.giphytest.data.repository.ElementRepository
import ua.org.kerzoll.giphytest.util.Resource

class ElementViewModel @ViewModelInject constructor(
    private val repository: ElementRepository
) : ViewModel() {

    companion object {
        const val TAG = "ElementViewModel"
        var currentId: String? = null
    }

    var elementsForActual: LiveData<Resource<ElementEntityRemote>> = MutableLiveData<Resource<ElementEntityRemote>>()

    fun search(query: String, limit: Int, offset: Int) {
        elementsForActual = repository.searchGif(query, limit, offset)
    }

    fun setCurrentId(id: String) {
        currentId = id
    }

    fun getCurrentId(): String? {
        return currentId
    }
}