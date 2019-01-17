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
import anwarabdullahn.com.villacorp_apps.API.AnwAPI
import anwarabdullahn.com.villacorp_apps.API.AnwCallback
import anwarabdullahn.com.villacorp_apps.API.AnwError
import anwarabdullahn.com.villacorp_apps.Activity.DOP.DOPActivity
import anwarabdullahn.com.villacorp_apps.Activity.IKS.IKSActivity
import anwarabdullahn.com.villacorp_apps.Activity.InOut.InOutActivity
import anwarabdullahn.com.villacorp_apps.Activity.LeaveFinger.LeaveFingerActivity
import anwarabdullahn.com.villacorp_apps.Activity.TukarLibur.TukarLiburActivity
import anwarabdullahn.com.villacorp_apps.Adapter.AgendaRecyclerAdapter
import anwarabdullahn.com.villacorp_apps.Adapter.InfoVPAdapter
import anwarabdullahn.com.villacorp_apps.Adapter.SliderVPAdapter
import anwarabdullahn.com.villacorp_apps.Model.AgendaSlider
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.AnwLoadingHelper
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.*

class HomeFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var infoViewPager: ViewPager
    lateinit var bottomSheetHome : ConstraintLayout
    lateinit var scrollView: ScrollView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    lateinit var adapter: SliderVPAdapter
    lateinit var adapterInfo: InfoVPAdapter
    lateinit var adapterAgenda: AgendaRecyclerAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var timer: Timer
    var sliderSize: Int = 0
    var infoSize: Int = 0
    var loadingScreen: DialogFragment = AnwLoadingHelper.getInstance()

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
        infoViewPager= contentView.find(R.id.infoViewPager)
        loadingScreen.show(fragmentManager,"loading Screen")
        val dotsIndicator = contentView.findViewById<WormDotsIndicator>(R.id.dots_indicator)
        val dotsIndicatorInfo = contentView.findViewById<WormDotsIndicator>(R.id.dots_indicator_info)

        AnwAPI.service().agendaSlider().enqueue(object : AnwCallback<AgendaSlider>() {
            override fun onSuccess(t: AgendaSlider) {
                loadingScreen.dismiss()
                adapter = SliderVPAdapter(contentView.context, t.slider)
                adapterInfo = InfoVPAdapter(contentView.context, t.info)
                adapterAgenda = AgendaRecyclerAdapter(t.agenda)
                sliderSize = t.slider.size
                infoSize = t.info.size
                recyclerView.adapter = adapterAgenda
                viewPager.adapter = adapter
                infoViewPager.adapter = adapterInfo
                dotsIndicator.setViewPager(viewPager)
                dotsIndicatorInfo.setViewPager(infoViewPager)

                timer = Timer()
                timer.scheduleAtFixedRate(MyTimerTask(),2000,4000)
            }

            override fun onError(error: AnwError?) {
                loadingScreen.dismiss()
                activity!!.toast(error?.msg.toString())
            }
        })


//        HRMS Menu
        contentView.tukarLiburHomeBtn.setOnClickListener {
            val intent = Intent(contentView.context, TukarLiburActivity::class.java)
            startActivity(intent)
        }

        contentView.telatPulangBtn.setOnClickListener {
            val intent = Intent(contentView.context, InOutActivity::class.java)
            startActivity(intent)
        }

        contentView.dopBtn.setOnClickListener {
            val intent = Intent(contentView.context, DOPActivity::class.java)
            startActivity(intent)
        }

        contentView.cutiDibayarBtn.setOnClickListener {
            val intent = Intent(contentView.context, LeaveFingerActivity::class.java)
            startActivity(intent)
        }

        contentView.keluarSementaraBtn.setOnClickListener {
//            toast("apa")
            val intent = Intent(contentView.context, IKSActivity::class.java)
            startActivity(intent)
        }


        return contentView
    }

    inner class MyTimerTask : TimerTask() {
        var page = 0
        var pageinfo = 0
        override fun run() {
            if (activity == null)
                return
            activity!!.runOnUiThread {
                if (page > sliderSize){
                    page = 0
                } else {
                    viewPager.currentItem = page ++
                }

                if (pageinfo > infoSize){
                    pageinfo = 0
                } else {
                    infoViewPager.currentItem = pageinfo ++
                }
            }
        }
    }

}
