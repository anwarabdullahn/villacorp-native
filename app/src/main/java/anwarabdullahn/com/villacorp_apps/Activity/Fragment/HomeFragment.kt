package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.*
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.Activity.DashboardActivity
import anwarabdullahn.com.villacorp_apps.Activity.TukarLibur.TukarLiburActivity
import anwarabdullahn.com.villacorp_apps.Adapter.AgendaRecyclerAdapter
import anwarabdullahn.com.villacorp_apps.Adapter.SliderVPAdapter
import anwarabdullahn.com.villacorp_apps.Model.AgendaSlider
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.*



class HomeFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var bottomSheetHome : ConstraintLayout
    lateinit var scrollView: ScrollView
    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    lateinit var adapter: SliderVPAdapter
    lateinit var adapterAgenda: AgendaRecyclerAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var timer: Timer
    var sliderSize: Int = 0
    var loadingScreen: DialogFragment = LoadingHelper.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_home, null)

        scrollView = contentView.find(R.id.scrollViewHome)
        scrollView.smoothScrollTo(0,0)
        bottomSheetHome = activity!!.find(R.id.bottomSheetHome)
        bottomSheetBehavior = from(bottomSheetHome)
        bottomSheetBehavior.state = STATE_HIDDEN

        contentView.lainnyaHomeBtn.setOnClickListener{
            bottomSheetBehavior.state = STATE_EXPANDED
        }


        recyclerView = contentView.find(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(contentView.context, LinearLayout.HORIZONTAL,false)
        recyclerView.setHasFixedSize(true)

        viewPager = contentView.find(R.id.viewPager)
        loadingScreen.show(fragmentManager,"loading Screen")
        val dotsIndicator = contentView.findViewById<WormDotsIndicator>(R.id.dots_indicator)

        API.service().agendaSlider().enqueue(object : APICallback<AgendaSlider>() {
            override fun onSuccess(t: AgendaSlider) {
                loadingScreen.dismiss()
                adapter = SliderVPAdapter(contentView.context, t.slider)
                adapterAgenda = AgendaRecyclerAdapter(t.agenda)
                sliderSize = t.slider.size
                recyclerView.adapter = adapterAgenda
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


//        HRMS Menu
        contentView.tukarLiburHomeBtn.setOnClickListener {
            val intent = Intent(contentView.context, TukarLiburActivity::class.java)
            startActivity(intent)
        }


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
