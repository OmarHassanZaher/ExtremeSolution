package com.bm.extremesolution.presentation.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.extremesolution.data.model.body.Cart
import com.bm.extremesolution.databinding.CartItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CartAdapter @Inject constructor(@ApplicationContext val context: Context):
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private lateinit var binding : CartItemBinding
    private var itemList: MutableList<Cart> = mutableListOf()


    inner class ViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cart: Cart) {
            binding.apply {
                title.text = cart.title
                price.text = "$${cart.price}"

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = itemList[position]
        holder.bind(cart)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateCartList(newCartList: MutableList<Cart>) {
        itemList.clear()
        itemList.addAll(newCartList)
        notifyDataSetChanged()
    }

    fun getTotalCost(): Double {
        var total = 0.0
        for (cart in itemList) {
            total += cart.price * cart.quantity
        }
        return total
    }
}