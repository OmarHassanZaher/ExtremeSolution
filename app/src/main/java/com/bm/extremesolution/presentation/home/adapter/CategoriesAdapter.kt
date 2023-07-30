package com.bm.extremesolution.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bm.extremesolution.R
import com.bm.extremesolution.data.model.response.CategoriesResponse
import com.bm.extremesolution.databinding.TextListItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CategoriesAdapter @Inject constructor(@ApplicationContext val context: Context) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private lateinit var binding: TextListItemBinding
    var categoriesList: List<String> = emptyList()
    var clickedPosition: Int = 0
    private var listener: CategoriesListListener? = null

    inner class ViewHolder(val binding: TextListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.text.setOnClickListener(this)
        }
        override fun onClick(view: View) {
            when (view.id) {
                R.id.text -> {
                    clickedPosition = adapterPosition
                    listener?.onCategoriesListClick(categoriesList[adapterPosition])
                    notifyDataSetChanged()
                }
                else -> {
                }
            }
        }
        fun bind(item: String) {
            binding.text.text = item
            binding.whatsOnCard.setOnClickListener {
                listener?.onCategoriesListClick(item)
            }
            if (adapterPosition == clickedPosition) {
                binding.text.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                binding.text.setBackgroundResource(R.drawable.button_shape)
            } else {
                binding.text.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                binding.text.setBackgroundResource(R.drawable.button_shape_white)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        binding = TextListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        holder.bind(categoriesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    fun setCategories(listener: CategoriesListListener) {
        this.listener = listener
    }

    fun setData(list: CategoriesResponse?) {
        this.categoriesList = list!!
        notifyDataSetChanged()
    }
}