package com.example.yodemo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yodemo.databinding.FragmentHomeBinding
import com.example.yodemo.ui.adapter.PopularVideosAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

   private var _binding: FragmentHomeBinding? = null
   private val binding by lazy { _binding!! }
   private val viewModel by viewModels<HomeViewModel>()
   private lateinit var homeAdapter: PopularVideosAdapter

   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
      _binding = FragmentHomeBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(
       view: View,
       savedInstanceState: Bundle?
   ) {
      super.onViewCreated(view, savedInstanceState)

      setupRecyclerView()
      observeDataFromYoDemo()

   }

   private fun observeDataFromYoDemo() {
         viewModel.popularVideos.observe(viewLifecycleOwner){ response ->
            homeAdapter.differ.submitList(response.data?.items)

      }

   }

   private fun setupRecyclerView() {
      homeAdapter = PopularVideosAdapter()
      binding.homeRv.apply {
         adapter = homeAdapter
         layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      }
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

}