package com.example.serhiiproject.presentation

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
import com.example.serhiiproject.data.remote.Common
import com.example.serhiiproject.data.remote.RetrofitServices
import com.example.serhiiproject.data.remote.model.VideoData
import com.example.serhiiproject.databinding.FragmentFirstBinding
import com.example.serhiiproject.local.ItemViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var mService: RetrofitServices
    private lateinit var adapter: ItemViewHolder
    private lateinit var recyclerView: RecyclerView


    private val binding get() = _binding!!
    private lateinit var viewModel: FirstFragmentViewModel

    private lateinit var swipe: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[FirstFragmentViewModel::class.java]
        swipe = binding.swipe
        viewModel.updateInformation()
        val workerScope = CoroutineScope(Dispatchers.IO)
        workerScope.launch {
            delay(1000)
            swipe.isRefreshing = false
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        mService = Common.retrofitService

        adapter = ItemViewHolder(arrayListOf(), ::processToSecondActivity)
        adapter.setData(arrayListOf())
        recyclerView.adapter = adapter

        swipe.setOnRefreshListener() {
            viewModel.updateInformation()
            val workerScope = CoroutineScope(Dispatchers.IO)
            workerScope.launch {
                delay(1000)
                swipe.isRefreshing = false
            }
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
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}