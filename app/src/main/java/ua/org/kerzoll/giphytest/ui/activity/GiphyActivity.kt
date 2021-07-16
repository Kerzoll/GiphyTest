package ua.org.kerzoll.giphytest.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import dagger.hilt.android.AndroidEntryPoint
import ua.org.kerzoll.giphytest.R
import ua.org.kerzoll.giphytest.data.entites.elements.ElementEntityRemote
import ua.org.kerzoll.giphytest.data.entites.elements.ElementGiphy
import ua.org.kerzoll.giphytest.data.viewmodel.ElementViewModel
import ua.org.kerzoll.giphytest.databinding.ActivityGiphyBinding
import ua.org.kerzoll.giphytest.databinding.ItemGiphyBinding
import ua.org.kerzoll.giphytest.util.Resource

@AndroidEntryPoint
class GiphyActivity : AppCompatActivity() {

    companion object {
        var adapterGroup = GroupAdapter<GroupieViewHolder>()
        var providerList: ElementEntityRemote? = null
    }

    private val elementViewModel: ElementViewModel by viewModels()

    private lateinit var elementsRv: RecyclerView
    lateinit var layoutManager : LinearLayoutManager
    private var pastVisiblesItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = false
    private var offset = 0
    private var searchText = "kotlin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Giphy Test Elements"

        val bindingElement =
            DataBindingUtil.setContentView<ActivityGiphyBinding>(this, R.layout.activity_giphy)

        layoutManager = LinearLayoutManager(this)
        elementsRv = bindingElement.elementRv
        elementsRv.layoutManager = layoutManager

        setupGroupAdapter()

        elementViewModel.search(searchText, 10, offset)
        getObserveResult()

        bindingElement.searchBtn.setOnClickListener {
            offset = 0
            adapterGroup.clear()
            searchText = bindingElement.searchText.text.toString()
            elementViewModel.search(searchText, 10, offset)
            getObserveResult()
        }

        elementsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount()
                    totalItemCount = layoutManager.getItemCount()
                    pastVisiblesItems = layoutManager.findLastCompletelyVisibleItemPosition()

                    if (visibleItemCount + pastVisiblesItems >= totalItemCount && loading) {
                        Log.d("GiphyActive", "The End!")
                        loading = false
                        offset += 10
                        elementViewModel.search(searchText, 10, offset)
                        getObserveResult()
                    }
                }
            }
        })
    }

    private fun getObserveResult() {
        elementViewModel.elementsForActual.observe(this, Observer {
            Log.d("GiphyActive", it.data.toString())
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        setAdapterItems(it.data.data)
                        loading = true
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupGroupAdapter() {
        adapterGroup = GroupAdapter()
        elementsRv.adapter = adapterGroup
        elementsRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setAdapterItems(items: List<ElementGiphy>) {
        //adapterGroup.clear()
        if (!items.isNullOrEmpty()) {
            items.forEach {
                adapterGroup.add(OrderRow(it))
            }
        }
    }

    inner class OrderRow(private val item: ElementGiphy) : BindableItem<ItemGiphyBinding>() {

        override fun getLayout(): Int {
            return R.layout.item_giphy
        }

        override fun initializeViewBinding(view: View): ItemGiphyBinding {
            return ItemGiphyBinding.bind(view)
        }

        override fun bind(viewBinding: ItemGiphyBinding, position: Int) {

            viewBinding.imageView.setOnClickListener {
                item.id?.let {
                    elementViewModel.setCurrentId(it)
                }
                startActivity(Intent("ua.org.kerzoll.giphytest.ui.activity.CarouselActivity"))
            }

            Glide.with(this@GiphyActivity)
                .asGif()
                .load("https://media1.giphy.com/media/" + item.id + "/giphy.gif")
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(viewBinding.imageView)
        }

    }
}