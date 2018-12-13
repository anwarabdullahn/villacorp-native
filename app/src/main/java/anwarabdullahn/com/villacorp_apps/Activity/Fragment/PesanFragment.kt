package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.Adapter.PesanRecyclerAdapter
import anwarabdullahn.com.villacorp_apps.Model.Pesans
import anwarabdullahn.com.villacorp_apps.R

class  PesanFragment: Fragment(){



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contenView = LayoutInflater.from(container?.context).inflate(R.layout.fragment_pesan, container,false)
        val recyclerView = contenView.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(contenView.context, LinearLayout.VERTICAL,false)

        API.service().pesan().enqueue(object : APICallback<Pesans>() {
            override fun onSuccess(pesans: Pesans) {

                val adapter = PesanRecyclerAdapter(pesans.pesan)
                recyclerView.adapter = adapter
            }

            override fun onError(error: APIError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        return contenView
    }
}
