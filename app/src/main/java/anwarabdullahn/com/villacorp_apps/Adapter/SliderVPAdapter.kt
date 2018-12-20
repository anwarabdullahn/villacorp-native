package anwarabdullahn.com.villacorp_apps.Adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import anwarabdullahn.com.villacorp_apps.Model.Slider
import anwarabdullahn.com.villacorp_apps.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class SliderVPAdapter(var context: Context, var sliderList: MutableList<Slider> ): PagerAdapter() {

    internal lateinit var layoutInflater: LayoutInflater

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === `p1`
    }

    override fun getCount(): Int {
        return sliderList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater.inflate(R.layout.list_slider, null)
        val imageSlider = v.find<ImageView>(R.id.sliderView)
        val slider = sliderList[position]
        Picasso.get().load(slider.file).into(imageSlider)
        val vp = container as ViewPager
        vp.addView(v, 0)
        return v
    }



    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }


}