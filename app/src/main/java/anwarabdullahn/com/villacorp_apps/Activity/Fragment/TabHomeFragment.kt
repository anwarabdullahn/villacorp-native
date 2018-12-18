package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.Adapter.SliderVPAdapter
import anwarabdullahn.com.villacorp_apps.Model.AgendaSlider
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.*



class TabHomeFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var adapter: SliderVPAdapter
    lateinit var timer: Timer
    var sliderSize: Int = 0
    var loadingScreen: DialogFragment = LoadingHelper.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.tab_fragment_home, null)
        viewPager = contentView.find(R.id.viewPager)
        loadingScreen.show(fragmentManager,"loading Screen")
        val dotsIndicator = contentView.findViewById<WormDotsIndicator>(R.id.dots_indicator)

        API.service().agendaSlider().enqueue(object : APICallback<AgendaSlider>() {
            override fun onSuccess(t: AgendaSlider) {
                loadingScreen.dismiss()
                adapter = SliderVPAdapter(contentView.context, t.slider)
                sliderSize = t.slider.size
                viewPager.adapter = adapter
                dotsIndicator.setViewPager(viewPager)

                timer = Timer()
                timer.scheduleAtFixedRate(MyTimerTask(),2000,4000)
            }

            override fun onError(error: APIError?) {
                loadingScreen.dismiss()
                activity!!.toast(error?.msg.toString())
            }
        })

        return contentView
    }

    inner class MyTimerTask : TimerTask() {
        var page = 0
        override fun run() {
            if (activity == null)
                return
            activity!!.runOnUiThread {
                if (page > sliderSize){
                    page = 0
                } else {
                    viewPager.currentItem = page ++
                }
            }
        }
    }

}
