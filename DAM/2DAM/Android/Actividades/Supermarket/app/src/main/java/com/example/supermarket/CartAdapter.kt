package com.example.supermarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val products: List<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCartProductName: TextView = itemView.findViewById(R.id.tvCartProductName)
        private val tvCartProductQuantity: TextView = itemView.findViewById(R.id.tvCartProductQuantity)
        private val tvCartProductTotalPrice: TextView = itemView.findViewById(R.id.tvCartProductTotalPrice)

        fun bind(product: Product) {
            tvCartProductName.text = product.name
            tvCartProductQuantity.text = "x${product.quantity}"
            tvCartProductTotalPrice.text = "${product.price * product.quantity}€"
        }
    }
}