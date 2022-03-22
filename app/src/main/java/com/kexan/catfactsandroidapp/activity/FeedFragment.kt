package com.kexan.catfactsandroidapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.kexan.catfactsandroidapp.R
import com.kexan.catfactsandroidapp.adapter.FactsAdapter
import com.kexan.catfactsandroidapp.adapter.OnInteractionListener
import com.kexan.catfactsandroidapp.databinding.FragmentFeedBinding
import com.kexan.catfactsandroidapp.dto.Fact
import com.kexan.catfactsandroidapp.viewmodel.FactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val factViewModel: FactViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val adapter = FactsAdapter(object : OnInteractionListener {
            override fun clickedOnCard(fact: Fact) {
                factViewModel.holdFact(fact)
                findNavController().navigate(
                    R.id.action_feedFragment_to_fullFactFragment
                )
            }

            override fun clickedOnShare(fact: Fact) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, fact.text)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_fact))
                startActivity(shareIntent)
            }

        })
        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            factViewModel.data.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                binding.swiperefresh.isRefreshing =
                    it.refresh is LoadState.Loading ||
                            it.prepend is LoadState.Loading ||
                            it.append is LoadState.Loading
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            adapter.refresh()
        }

        return binding.root
    }
}