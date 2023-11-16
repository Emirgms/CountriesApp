package com.example.mycountryapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mycountryapp.databinding.ItemCountryBinding
import com.example.mycountryapp.model.CountryModel
import com.example.mycountryapp.util.downloadFromUrl
import com.example.mycountryapp.util.placeHolderProgressBar
import com.example.mycountryapp.view.FeedFragmentDirections

class CountryAdapter(private val countryList: ArrayList<CountryModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }


    class ListViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CountryModel,context: Context) {
            binding.apply {
                countryName.text = item.countryName
                region.text = item.countryRegion
                item.imageUrl?.let { imageView.downloadFromUrl(it, placeHolderProgressBar(context)) }
            }

        }

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListViewHolder).bind(countryList[position],holder.itemView.context)
        holder.itemView.setOnClickListener {
            val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)

            Navigation.findNavController(it).navigate(action)
        }


    }

    fun updateCountryList(newCountryList: List<CountryModel>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }


}