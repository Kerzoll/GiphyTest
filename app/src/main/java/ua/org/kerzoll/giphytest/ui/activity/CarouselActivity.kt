package ua.org.kerzoll.giphytest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import ua.org.kerzoll.giphytest.R
import ua.org.kerzoll.giphytest.data.viewmodel.ElementViewModel
import ua.org.kerzoll.giphytest.util.Resource
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import ua.org.kerzoll.giphytest.data.entites.elements.ElementGiphy
import ua.org.kerzoll.giphytest.databinding.ActivityCarouselBinding
import ua.org.kerzoll.giphytest.databinding.ActivityGiphyBinding

@AndroidEntryPoint
class CarouselActivity : AppCompatActivity() {

    private val elementViewModel: ElementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carousel)
        val currentId = elementViewModel.getCurrentId()

        if (currentId != null) {
            Toast.makeText(this, "Item ID: $currentId", Toast.LENGTH_LONG).show()
            Glide.with(this@CarouselActivity)
                .asGif()
                .load("https://media1.giphy.com/media/$currentId/giphy.gif")
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(findViewById(R.id.imageView_slide))
        }

    }

}