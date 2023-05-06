package com.example.serhiiproject.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.serhiiproject.R
import com.example.serhiiproject.dataClasses.VideoData
import com.example.serhiiproject.databinding.FragmentFirstBinding
import com.example.serhiiproject.remote.Common
import com.example.serhiiproject.remote.RetrofitServices

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var mService: RetrofitServices
    private lateinit var adapter: ItemDataHolder
    private lateinit var recyclerView: RecyclerView


    private val binding get() = _binding!!
    private lateinit var viewModel: FirstFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[FirstFragmentViewModel::class.java]
        viewModel.updateInformation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipe)

        mService = Common.retrofitService

        adapter = ItemDataHolder(arrayListOf(), ::processToSecondActivity)
        adapter.setData(arrayListOf())
        recyclerView.adapter = adapter

        swipe.setOnRefreshListener {
            viewModel.updateInformation()
        }

        viewModel.videosData.observe(viewLifecycleOwner)
        {
            adapter.setData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner)
        {
            swipe.isRefreshing = true
        }
    }

    private fun processToSecondActivity(data: VideoData)
    {
        val bundle = bundleOf("description" to data.snippet!!.description, "videoURL" to "https://www.youtube.com/embed/${data.id}")
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle) // description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}