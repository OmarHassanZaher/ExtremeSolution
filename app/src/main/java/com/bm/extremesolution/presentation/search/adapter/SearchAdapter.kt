package com.bm.extremesolution.presentation.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.extremesolution.data.model.response.SearchResponse
import com.bm.extremesolution.databinding.SearchCardBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchAdapter @Inject constructor(@ApplicationContext val context: Context):
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private lateinit var binding : SearchCardBinding
    private var productsList:ArrayList<SearchResponse.productItem> = ArrayList()
    private var listener : SearchListener? = null

    inner class ViewHolder (binding: SearchCardBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchResponse.productItem) {
            binding.apply {
                title.text = item.title
                decription.text=item.description
                category.text=item.category
                searchCard.setOnClickListener {listener!!.onSearchClick(item)  }

            }
        }
    }
    fun setSearchListener(listener: SearchListener){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = SearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productsList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }
    fun setData(list: List<SearchResponse.productItem>) {
        if (this.productsList.isNotEmpty())
            this.productsList.clear()
        this.productsList.addAll(list)
        notifyDataSetChanged()
    }
}