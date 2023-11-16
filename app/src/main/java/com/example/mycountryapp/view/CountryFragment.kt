package com.example.mycountryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mycountryapp.R
import com.example.mycountryapp.databinding.FragmentCountryBinding
import com.example.mycountryapp.util.downloadFromUrl
import com.example.mycountryapp.util.placeHolderProgressBar
import com.example.mycountryapp.viewmodel.CountryViewModel
import com.example.mycountryapp.viewmodel.FeedViewModel


class CountryFragment : Fragment() {
    private  var countryUuid=0
    private val viewModel: CountryViewModel by viewModels()
    private lateinit var binding:FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCountryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid=CountryFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
            country->
            country?.let {
                binding.countryName.text=country.countryName
                binding.countryCapital.text=country.countryCapital
                binding.countryCurrency.text=country.countryCurrency
                binding.countryRegion.text=country.countryRegion
                context?.let {
                    country.imageUrl?.let {
                            imageUrl->
                        binding.countryImage.downloadFromUrl(imageUrl, placeHolderProgressBar(it))
                    }
                }



            }
        })
    }
    }
