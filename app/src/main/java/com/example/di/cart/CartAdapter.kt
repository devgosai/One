package com.example.di.cart

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.di.R
import com.example.di.databinding.ItemCartProductListBinding

class CartAdapter(var context: Context, var list: ArrayList<Product>, val callback: Callback) : RecyclerView.Adapter<CartAdapter.Holder>() { class Holder(var binding: ItemCartProductListBinding) :
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding: ItemCartProductListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.item_cart_product_list, parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = list[position]

        holder.binding.tvPosition.text = data.productId.toString()
        holder.binding.tvProductName.text = data.productNane
        holder.binding.etQty.setText(data.productQty.toString())
        holder.binding.etPrice.setText(data.productPrice.toString())
        holder.binding.tvTotal.text = data.total.toString()

        // Save the entered text when it changes
        holder.binding.etQty.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update the entered text in the data structure
                callback.sendQty(
                    s.toString().toDoubleOrNull() ?: 0.0, holder.adapterPosition
                )
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used
                totalCalculation(
                    s.toString().toDoubleOrNull() ?: 0.0, holder.binding.etPrice.text.toString().toDoubleOrNull() ?: 0.0, holder
                )
            }
        })

        holder.binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update the entered text in the data structure
                callback.sendPrice(
                    s.toString().toDoubleOrNull() ?: 0.0, holder.adapterPosition
                )
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used
                totalCalculation(
                    holder.binding.etQty.text.toString().toDoubleOrNull() ?: 0.0, s.toString().toDoubleOrNull() ?: 0.0, holder
                )
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface Callback {

        fun sendQty(qty: Double, position: Int)
        fun sendPrice(price: Double, position: Int)
        fun sendTotal(total: Double, position: Int)
    }

    private fun totalCalculation(qty: Double, price: Double, holder: Holder) {
        holder.binding.tvTotal.text = (qty * price).toString()
        callback.sendTotal((qty * price), holder.adapterPosition)
    }
}