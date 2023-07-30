package com.bm.extremesolution.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse
import com.bm.extremesolution.databinding.ProductsCartItemBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProductsAdapter @Inject constructor(@ApplicationContext val context: Context):
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private lateinit var binding : ProductsCartItemBinding
    private var productsList:ArrayList<ProductsByCategoryResponse.ProductsByCategoryResponseItem> = ArrayList()
    private var listener: ProductListListener? = null
    inner class ViewHolder (binding: ProductsCartItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductsByCategoryResponse.ProductsByCategoryResponseItem) {
            binding.apply {
                title.text = item.title
                Glide.with(binding.image.context).load(item.image).into(image)
                productCart.setOnClickListener { listener!!.onProductListClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ProductsCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productsList[position])
        holder.setIsRecyclable(false)
    }
    fun setProducts(listener: ProductListListener) {
        this.listener = listener
    }
    override fun getItemCount(): Int {
        return productsList.size
    }
    fun setData(list: List<ProductsByCategoryResponse.ProductsByCategoryResponseItem>) {
        if (this.productsList.isNotEmpty())
            this.productsList.clear()
        this.productsList.addAll(list)
        notifyDataSetChanged()
    }
}