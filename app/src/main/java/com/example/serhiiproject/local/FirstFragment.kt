package com.example.serhiiproject.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serhiiproject.R
import com.example.serhiiproject.databinding.FragmentFirstBinding
import com.example.serhiiproject.local.dataClasses.PopularVideosData
import com.example.serhiiproject.local.dataClasses.VideoData
import com.example.serhiiproject.remote.Common
import com.example.serhiiproject.remote.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var mService: RetrofitServices
    private lateinit var adapter: ItemDataHolder
    private lateinit var recyclerView: RecyclerView


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        mService = Common.retrofitService



        adapter = ItemDataHolder(arrayListOf(), ::processToSecondActivity)
        adapter.setData(arrayListOf())
        recyclerView.adapter = adapter
        getPopularVideos()
    }

    private fun processToSecondActivity(data: VideoData)
    {
        val bundle = bundleOf("description" to data.snippet!!.description, "videoURL" to "https://www.youtube.com/embed/${data.id}")
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle) // description
    }

    private fun getPopularVideos() {
        adapter.setData(arrayListOf())
        mService.getPopularVideos().enqueue(object : Callback<PopularVideosData> {
            override fun onFailure(call: Call<PopularVideosData>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<PopularVideosData>,
                response: Response<PopularVideosData>
            ) {
                val resp: PopularVideosData = response.body() as PopularVideosData

                resp.items?.forEach()
                {
                    adapter.addData(
                        VideoData(
                            it.snippet!!.title,
                            "https://img.youtube.com/vi/${it.id}/0.jpg",
                            it.snippet,
                            it.id
                        )
                    )
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}