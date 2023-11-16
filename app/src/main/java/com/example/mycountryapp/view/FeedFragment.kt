package com.example.mycountryapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycountryapp.adapter.CountryAdapter
import com.example.mycountryapp.databinding.FragmentFeedBinding
import com.example.mycountryapp.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private val countryAdapter = CountryAdapter(arrayListOf())
   private val viewModel:FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    binding = FragmentFeedBinding.inflate(inflater, container, false)
    return binding.root
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        viewModel.refreshData()
        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility=View.GONE
            binding.errorText.visibility=View.GONE
            binding.countryLoading.visibility=View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing=false
        }
    }

  private  fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    binding.errorText.visibility = View.VISIBLE
                } else {
                    binding.errorText.visibility = View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                } else {
                    binding.countryLoading.visibility = View.GONE
                }
            }
        })
    }


}