package ua.org.kerzoll.giphytest.ui.activity

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import ua.org.kerzoll.giphytest.R
import ua.org.kerzoll.giphytest.data.viewmodel.ElementViewModel


@AndroidEntryPoint
class CarouselActivity : AppCompatActivity() {

    private val elementViewModel: ElementViewModel by viewModels()

    var startXValue: Float = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carousel)
        val currentId = elementViewModel.getCurrentId()
        if (currentId != null) {
            setImage(currentId)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var endXValue = 0f
        val x1 = event!!.getAxisValue(MotionEvent.AXIS_X)
        val action = MotionEventCompat.getActionMasked(event)
        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                startXValue = event!!.getAxisValue(MotionEvent.AXIS_X)
                true
            }
            MotionEvent.ACTION_UP -> {
                endXValue = event!!.getAxisValue(MotionEvent.AXIS_X)
                val currentKey = elementViewModel.getCurrentId()?.let {
                    elementViewModel.getKeyById(
                        it
                    )
                }
                if (endXValue > startXValue) {
                    if (endXValue - startXValue > 100) {
                        println("Left-Right")
                        currentKey?.let {
                            if(currentKey > 0) {
                                elementViewModel.setCurrentId(elementViewModel.getIds()[currentKey - 1])
                            }
                        }
                        println(elementViewModel.getIds())
                        //imageView.setImageResource(mImageIds.get(2))
                    }
                } else {
                    if (startXValue - endXValue > 100) {
                        currentKey?.let {
                            if(currentKey < elementViewModel.getIds().size) {
                                elementViewModel.setCurrentId(elementViewModel.getIds()[currentKey + 1])
                            }
                        }

                        println("Right-Left")
                        //imageView.setImageResource(mImageIds.get(0))
                    }
                }

                val currentId = elementViewModel.getCurrentId()
                if (currentId != null) {
                    setImage(currentId)
                }

                true
            }
            else -> super.onTouchEvent(event)
        }

    }

    private fun setImage(id: String) {
        Toast.makeText(this, "Item ID: $id", Toast.LENGTH_LONG).show()
        Glide.with(this@CarouselActivity)
            .asGif()
            .load("https://media1.giphy.com/media/$id/giphy.gif")
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(findViewById(R.id.imageView_slide))
    }
}
