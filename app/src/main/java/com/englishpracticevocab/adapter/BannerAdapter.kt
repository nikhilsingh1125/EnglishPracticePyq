package com.englishpracticevocab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.englishpracticevocab.model.HomeBannerData
import kotlinx.android.synthetic.main.image_view_banner.view.imageViewMain
import java.util.Objects


internal class BannerAdapter(context: Context, images: ArrayList<HomeBannerData>) :
    PagerAdapter() {
    // Context object
    var context: Context

    // Array of images
    var images: ArrayList<HomeBannerData>

    // Layout Inflater
    var mLayoutInflater: LayoutInflater

    // Viewpager Constructor
    init {
        this.context = context
        this.images = images
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        // return the number of images
        return images.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView: View = mLayoutInflater.inflate(com.englishpracticevocab.R.layout.image_view_banner, container, false)

        val model = images[position]

        val requestOptions = RequestOptions().transform(RoundedCorners(20))
        model.let {
            Glide.with(context)
                .load(model.banner)
                .apply(requestOptions)
                .into(itemView.imageViewMain)
        }


        // Adding the View
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}